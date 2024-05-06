package com.xiaozhi.aoaojiao.model.dto.dept;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * @author xiaozhi
 */
@Data
public class SysDeptDeleteDTO {
    
    @Size(min = 1, message = "至少选择一个部门")
    private List<Long> ids;
}
