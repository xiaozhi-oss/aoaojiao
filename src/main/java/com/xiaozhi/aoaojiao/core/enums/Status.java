package com.xiaozhi.aoaojiao.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaozhi
 */
@Getter
@AllArgsConstructor
public enum Status {
    
    ENABLE(1, "开启"),
    UN_ENABLE(0, "禁用");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态
     */
    private final String msg;
    
}
