package com.huanshare.test.api.gateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.huanshare.test.client.common.support.exception.testServiceException;
import com.huanshare.test.client.common.support.msgcode.MsgCode;
import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * 限流拦截器
 *
 * created on 2018/5/28 19:22
 *
 *
 * @version 1.0.0
 */
@Component
public class RateLimterFilter extends ZuulFilter {
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(500);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        if (!RATE_LIMITER.tryAcquire()) {
            throw new testServiceException(MsgCode.test_SYS_SERVICE_ERROR);
        }
        return null;
    }
}
