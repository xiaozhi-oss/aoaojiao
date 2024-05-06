package com.xiaozhi.aoaojiao.model.dto.dept;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

/**
 * @author xiaozhi
 */
@Data
public class SysDeptAddOrUpdateDTO {
    
    private Long deptId;

    /**
     * 部门名
     */
    @NotBlank(message = "部门名不能为空")
    private String deptName;

    /**
     * 排序index
     */
    private Integer deptSort;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 父部门名字
     */
    private String parentName;


    /**
     * 状态：0-禁用；1-启用
     */
    private Integer status;

    @NotBlank(message = "部门名不能为空")
    private String leader;

    private String email;

    private String phone;

    private String remark;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;
}
