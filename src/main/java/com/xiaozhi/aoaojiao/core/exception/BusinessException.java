package com.xiaozhi.aoaojiao.core.exception;

import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.function.Supplier;

/**
 * @author xiaozhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private ResponseStatus responseStatus;
    public BusinessException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }
    public static BusinessException build(ResponseStatus responseStatus) {
        return new BusinessException(responseStatus);
    }

    public static Supplier buildError(ResponseStatus responseStatus) {
        return () -> BusinessException.build(responseStatus);
    }
}
