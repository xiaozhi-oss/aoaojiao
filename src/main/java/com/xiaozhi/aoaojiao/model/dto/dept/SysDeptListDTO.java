package com.xiaozhi.aoaojiao.model.dto.dept;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author xiaozhi
 */
@Data
public class SysDeptListDTO {
    
    private String deptName;
    
    @Range(min = 0, max = 1, message = "状态值错误")
    private Integer status;
    
}
