package com.huanshare.test.api.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.Config;
import com.huanshare.test.client.common.support.ResponseEntity;
import com.huanshare.test.client.common.support.base.service.CacheService;
import com.huanshare.test.client.common.support.constant.RedisConstant;
import com.huanshare.test.client.common.support.constant.RequestConstant;
import com.huanshare.test.client.common.support.enums.BusinessCode;
import com.huanshare.test.client.common.support.msgcode.MsgCode;
import com.huanshare.test.client.common.util.HttpUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * token过滤器
 */
@Component
@Slf4j
public class TokenFilter extends ZuulFilter {
    /**
     * 忽略拦截url
     */
    private final static List<String> ignoreUrl = Arrays.asList("/user/user/login",
            "/user/user/logout",
            "/user/v2/api-docs",
            "/business/v2/api-docs",
            "/business/common/download",
            "/user/app/init",
            "/user/user/findPwd",
            "/business/api/**",
            "/user/api/**");

    private final static String FILE_HEADER = "multipart/form-data";

    @Autowired
    private CacheService cacheService;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        final String traceLogId = generatorLogId();
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String uri = request.getRequestURI();
        log.info("请求路径 uri:{}", uri);
        try {
            ServletInputStream inputStream = request.getInputStream();
            String body = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
            JSONObject jsonObject = JSON.parseObject(body);
            if (!checkXss(jsonObject, uri)) {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.OK.value());
                requestContext.setResponseBody(JSON.toJSONString(ResponseEntity.msg(String.valueOf(BusinessCode.test_XSS_ERROR.getCode()), BusinessCode.test_XSS_ERROR.getName()).build()));
                requestContext.getResponse().setCharacterEncoding("utf-8");
                return null;
            }
        } catch (Exception e) {
            log.error("json 异常 {}", e.getMessage());
        }
        String ip = HttpUtil.clientIP(request);
        if (!hasIgnoreUrl(uri)) {
            String token = "";
            String contentType = request.getContentType();
            //文件上传请求 *特殊请求
            if (contentType.contains(FILE_HEADER)) {
                MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
                MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
                token = multipartRequest.getParameter(RequestConstant.test_USER_TOKEN);
            } else {
                //非文件上传请求
                try {
                    ServletInputStream inputStream = request.getInputStream();
                    String body = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
                    JSONObject jsonObject = JSON.parseObject(body);
                    token = jsonObject.getString(RequestConstant.test_USER_TOKEN);
                } catch (Exception e) {
                    log.error("json 异常 {}", e.getMessage());
                }
                //这里从url参数里获取, 也可以从cookie, header里获取
                if (StringUtils.isBlank(token)) {
                    token = request.getHeader(RequestConstant.test_USER_TOKEN);
                }
                if (StringUtils.isBlank(token)) {
                    token = request.getParameter(RequestConstant.test_USER_TOKEN);
                }
                if (StringUtils.isBlank(token)) {
                    token = (String) request.getAttribute(RequestConstant.test_USER_TOKEN);
                }
            }
            String userId = null;
            if (StringUtils.isBlank(token) ||
                    StringUtils.isEmpty((userId = cacheService.get(cacheService.wrapKey(RedisConstant.TOKEN_TEMPLATE, token), String.class)))) {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.OK.value());
                requestContext.setResponseBody(JSON.toJSONString(ResponseEntity.code(MsgCode.test_SYS_INVALID_LOGIN).build()));
                HttpServletResponse response = requestContext.getResponse();
                response.setCharacterEncoding("utf-8");
                log.warn("请求路径 uri:{} 登陆未授权  token:【{}】 userId:【{}】", uri, token, userId);
            } else {
                requestContext.addZuulRequestHeader(RequestConstant.LOGIN_USER_ID, userId);
                log.info("请求路径 uri:{} 【{}】登陆授权通过  token:【{}】 userId:【{}】", uri, ip, token, userId);
            }
        }
        requestContext.addZuulRequestHeader(RequestConstant.CLIENT_IP, ip);
        requestContext.addZuulRequestHeader(SystemMarker.TRACE_LOG_ID, traceLogId);
        return null;
    }

    private String generatorLogId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private boolean hasIgnoreUrl(String uri) {
        for (String url : ignoreUrl) {
            if (StringUtils.equals(url, uri)) {
                log.info("【{}】忽略拦截", uri);
                return true;
            }
            int index;
            if ((index = StringUtils.indexOf(url, "**")) > 0) {
                String start = url.substring(0, index);
                if (uri.startsWith(start)) {
                    log.info("满足start：【{}】规则，【{}】忽略拦截", start, uri);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkXss(JSONObject jsonObject, String uri) {
        String xssChar = getXssChar();
        if (!CollectionUtils.isEmpty(jsonObject)) {
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                if (entry == null || entry.getValue() == null) {
                    continue;
                }
                if (StringUtils.isBlank(xssChar)) {
                    break;
                }
                String value = entry.getValue().toString();
                for (String s : xssChar.split(",")) {
                    if (value.toLowerCase().contains(s) || value.toLowerCase().contains(HtmlUtils.htmlEscape(s))) {
                        log.error("uri:【{}】请求参数【{}】存在异常数据【{}】", uri, value, s);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Autowired
    @Qualifier("config")
    private Config apolloConfig;

    private String getXssChar() {
        return apolloConfig.getProperty("xss.char", "");
    }
}
