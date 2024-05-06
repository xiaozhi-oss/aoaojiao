package com.xiaozhi.aoaojiao.core.security.handler;

import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xiaozhi
 *
 * 处理没有权限错误，解决 AccessDeniedHandler 不起效问题
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccessDeniedExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AccessDeniedExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult<Void> handlerAccessDeniedException (AccessDeniedException  e) {
        return ResponseResult.fail(ResponseStatus.USER_UN_UNAUTHORIZED);
    }
}
