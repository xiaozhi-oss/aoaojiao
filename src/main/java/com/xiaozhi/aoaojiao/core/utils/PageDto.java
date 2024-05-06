package com.xiaozhi.aoaojiao.core.utils;

import lombok.Data;

/**
 * @author xiaozhi
 */
@Data
public class PageDto {

    /**
     * 当前页码
     */
    private Integer pageNo = 1;


    /**
     * 页数
     */
    private Integer pageSize = 10;
}
