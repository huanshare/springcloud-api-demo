package com.huanshare.test.business.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * http 拦截器
 *
 * created on 2018/4/23 17:01
 *
 *
 * @version 1.0.0
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.info("请求 ：【{}】开始", uri);
        request.setAttribute("requestTime", System.currentTimeMillis());
        MDC.put(SystemMarker.TRACE_LOG_ID, request.getHeader(SystemMarker.TRACE_LOG_ID));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String uri = request.getRequestURI();
        if (ex != null) {
            log.error("请求 ：" + uri + " 异常，Exception：{}", ex);
        }
        String requestTime = String.valueOf(request.getAttribute("requestTime"));
        Long useTime = System.currentTimeMillis() - Long.parseLong(requestTime);
        log.info("请求 ：【{}】结束，耗时【{}】", uri, useTime);
        if (useTime > 5000) {
            log.info("请求：【{}】耗时超过5秒，实际耗时【{}】", uri, useTime);
        }
    }
}
