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
@TableName("sys_role")
public class SysRole {

    /**
     * 主键ID
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @TableField("role_str")
    private String roleStr;

    /**
     * 角色排序值，根据此排序值进行排序展示
     */
    @TableField("role_sort")
    private Integer roleSort;

    /**
     * 状态：0-禁用；1-启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 删除标记：0-存在；1-删除
     */
    @TableField("def_falg")
    private Integer defFalg;

    @TableField("create_by")
    private Long createBy;

    @TableField("update_by")
    private Long updateBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    /**
     * 描述
     */
    @TableField("remark")
    private String remark;


}
