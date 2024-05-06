package com.xiaozhi.aoaojiao.core.exception;

import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author xiaozhi
 *
 * 参数校验异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParameterVerificationException extends RuntimeException{

    private List<String> parameters;

    public ParameterVerificationException(List<String> parameters) {
        super(ResponseStatus.PARAMETER_VERIFICATION_ERROR.getMessage());
        this.parameters = parameters;
    }
}
