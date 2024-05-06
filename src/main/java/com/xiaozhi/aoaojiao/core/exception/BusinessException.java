package com.xiaozhi.aoaojiao.core.exception;

import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
}
