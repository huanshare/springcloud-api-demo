package com.huanshare.test.business.aop;

import com.huanshare.test.client.common.support.ResponseEntity;
import com.huanshare.test.client.common.support.exception.testException;
import com.huanshare.test.client.common.support.exception.testServiceException;
import com.huanshare.test.client.common.support.exception.UserNotAuthedException;
import com.huanshare.test.client.common.support.msgcode.MsgCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

/**
 *
 * created on 2017/11/14 11:00
 *
 *
 * @version 1.0.0
 */
@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    ResponseEntity runtimeExceptionHandler(Throwable e) {
        log.error("runtimeExceptionHandler:", e);
        return ResponseEntity.code(MsgCode.test_SYS_SERVICE_ERROR).build();
    }

    @ExceptionHandler({testServiceException.class})
    @ResponseBody
    ResponseEntity testServiceException(Throwable e) {
        log.error("testServiceException:", e);
        testServiceException credit = (testServiceException) e;
        return ResponseEntity.msg(credit.getCode(), credit.getMsg()).build();
    }

    @ExceptionHandler({testException.class})
    @ResponseBody
    ResponseEntity testException(Throwable e) {
        log.error("testException:", e);
        testException credit = (testException) e;
        return ResponseEntity.msg(String.valueOf(credit.getCode()), credit.getMessage()).build();
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    ResponseEntity illegalParamsExceptionHandler(Throwable e) {
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException message = ((MethodArgumentNotValidException) e);
            List<ObjectError> allErrors = message.getBindingResult().getAllErrors();
            allErrors.forEach(error -> {
                log.error("【{}】数据错误信息：【{}】", error.getObjectName(), error.getDefaultMessage());
            });
            if (!CollectionUtils.isEmpty(allErrors)) {
                return ResponseEntity.msg(MsgCode.test_DEFINED_ERROR.getCode(), allErrors.get(0).getDefaultMessage()).build();
            }
        } else {
            log.error("illegalParamsExceptionHandler:", e);
        }
        return ResponseEntity.code(MsgCode.test_SYS_INVAILD_PARAMETER).build();
    }

    @ExceptionHandler({MissingPathVariableException.class, MissingServletRequestParameterException.class})
    @ResponseBody
    ResponseEntity illegalPathExceptionHandler(Throwable e) {
        log.error("illegalPathExceptionHandler:", e);
        return ResponseEntity.code(MsgCode.test_SYS_MISS_PARAMETER).build();

    }

    @ExceptionHandler({UserNotAuthedException.class})
    @ResponseBody
    ResponseEntity notAuthedExceptionHandler(Throwable e) {
        log.error("notAuthedExceptionHandler:", e);
        return ResponseEntity.code(MsgCode.test_SYS_INVALID_LOGIN).build();
    }

}
