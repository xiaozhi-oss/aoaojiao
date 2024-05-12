package com.xiaozhi.aoaojiao.core.exception;

import cn.hutool.core.date.DateUtil;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaozhi
 * 
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { BindException.class, ValidationException.class,
            MethodArgumentNotValidException.class, ParameterVerificationException.class })
    public ResponseResult<List<String>> handleParameterVerificationException(@NonNull Exception e) {
        List<String> errorList = new ArrayList<>();
        if (e instanceof MethodArgumentNotValidException bindException) {
            BindingResult bindingResult = bindException.getBindingResult();
            bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .forEach(errorList::add);
        } else if (e instanceof  ParameterVerificationException pv) {
            errorList = pv.getParameters();
        } else {
            errorList.add("invalid parameter");
        }
        log.warn("参数校验异常：{}", errorList);
        return ResponseResult.response(ResponseStatus.PARAMETER_VERIFICATION_ERROR, errorList);
    }

    @ExceptionHandler(value = { BusinessException.class, NoResourceFoundException.class })
    public ResponseResult<Void> processBusinessException(Exception e) {
        log.warn("时间：{}，业务异常：{}", DateUtil.now(), e.getMessage());
        if (e instanceof BusinessException be) {
            return ResponseResult.fail(be.getResponseStatus());
        } else if (e instanceof  NoResourceFoundException) {
            return ResponseResult.fail(ResponseStatus.NOT_FOUND_ERROR);
        } else {
            return ResponseResult.fail(ResponseStatus.SYSTEM_ERROR);
        }
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<Void> handlerException(Exception e) {
        log.error("时间：{}，系统异常信息：{}", DateUtil.now(), e.getMessage());
        return ResponseResult.fail();
    }
}
