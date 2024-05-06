package com.xiaozhi.aoaojiao.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 10:20:23
 */
@Getter
@Setter
@TableName("sys_dept")
public class SysDept {

    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;

    /**
     * 部门名
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 排序index
     */
    @TableField("dept_sort")
    private Integer deptSort;

    /**
     * 父ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 状态：0-禁用；1-启用
     */
    @TableField("status")
    private Integer status;

    @TableField("remark")
    private String remark;

    @TableField("create_by")
    private Long createBy;

    @TableField("update_by")
    private Long updateBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;


}
