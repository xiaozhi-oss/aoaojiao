package com.xiaozhi.aoaojiao.core.utils;

import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import lombok.Builder;
import lombok.Data;

/**
 * @author xiaozhi
 * 
 * 响应结果对象封装
 */
@Data
@Builder
public class ResponseResult<T> {

    private String code;
    private String message;
    private T data;
    private long timestamp;
    public static <T> ResponseResult<T> response(ResponseStatus responseStatus, T data) {
        return ResponseResult.<T>builder()
                .data(data)
                .message(responseStatus.getMessage())
                .code(responseStatus.getCode())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static <T> ResponseResult<T> response(String code, String message, T data) {
        return ResponseResult.<T>builder()
                .data(data)
                .message(message)
                .code(code)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static <T> ResponseResult<T> success(T data) {
        return response(ResponseStatus.SUCCESS, data);
    }

    public static <T> ResponseResult<T> success() {
        return response(ResponseStatus.SUCCESS, null);
    }
    
    public static <T> ResponseResult<T> fail(ResponseStatus responseStatus) {
        return response(responseStatus, null);
    }

    public static <T> ResponseResult<T> fail() {
        return response(ResponseStatus.SYSTEM_ERROR, null);
    }
}
