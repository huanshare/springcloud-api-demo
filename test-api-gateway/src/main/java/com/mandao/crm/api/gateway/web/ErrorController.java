package com.huanshare.test.api.gateway.web;

import com.huanshare.test.client.common.support.ResponseEntity;
import com.huanshare.test.client.common.support.msgcode.MsgCode;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 封装异常信息
 *
 * created on 2018/5/29 20:08
 *
 *
 * @version 1.0.0
 */
@RestController
public class ErrorController {

    @GetMapping(value = "/error")
    public ResponseEntity error(HttpServletRequest request) {
        ZuulException exception = (ZuulException) request.getAttribute("javax.servlet.error.exception");
        return ResponseEntity.msg(MsgCode.test_SYS_SERVICE_ERROR.getCode(), exception.getMessage()).build();
    }
}
