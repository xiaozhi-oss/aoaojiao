package com.xiaozhi.aoaojiao.model.vo.dept;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author xiaozhi
 */
@Data
public class SysDeptVO {
    
    private Long deptId;

    @NotBlank(message = "部门名称不能为空")
    private String deptName;
    
    @Min(value = 0, message = "显示顺序不能小于0")
    @NotNull(message = "排序值不能为空")
    private Integer deptSort;
    
    private Long parentId;
    
    private String parentName;
    
    private Integer status;

    @NotBlank(message = "领导不能为空")
    private String leader;
    
    private String email;
    
    private String phone;
    
    private String remark;
    
    private Date createTime;
    
    private List<SysDeptVO> childrenList;
}
