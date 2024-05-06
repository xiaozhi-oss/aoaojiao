package com.xiaozhi.aoaojiao.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 部门
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 10:20:23
 */
@Data
@TableName("sys_dept")
public class SysDept {

    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;

    /**
     * 部门名
     */
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

    private String leader;

    private String email;

    private String phone;
    
    private String remark;
    
    private Long createBy;

    private Long updateBy;
    
    private Date createTime;
    
    private Date updateTime;
}
