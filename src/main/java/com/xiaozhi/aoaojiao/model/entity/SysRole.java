package com.xiaozhi.aoaojiao.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 10:20:23
 */
@Data
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
     * 状态：0-禁用；1-启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 删除标记：0-存在；1-删除
     */
    @TableField("def_flag")
    private Integer defFlag;

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

    /**
     * 菜单组
     */
    @TableField(exist = false)
    private List<Long> menuIds;

    /**
     * 角色菜单权限
     */
    @TableField(exist = false)
    private Set<String> permissions;
}
