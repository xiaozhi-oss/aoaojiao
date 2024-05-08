package com.xiaozhi.aoaojiao.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author xiaozhi
 */
@Data
public class SysMenuListDTO {

    private String menuName;

    @Range(min = 0, max = 1, message = "状态值错误")
    private Integer status;
}
