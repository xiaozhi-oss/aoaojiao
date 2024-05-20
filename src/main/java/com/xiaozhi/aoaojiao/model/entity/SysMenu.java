package com.xiaozhi.aoaojiao.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 10:20:23
 */
@Data
@TableName("sys_menu")
public class SysMenu {

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名字
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 父菜单ID，表示最顶层菜单
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 路由地址
     */
    @TableField("path")
    private String path;

    /**
     * 路由参数
     */
    @TableField("query")
    private String query;

    /**
     * 是否为外链：0-不是；1-是
     */
    @TableField("is_outer_chain")
    private Integer isOuterChain;

    /**
     * 菜单类型：0-目录；1-菜单；2-按钮
     */
    @TableField("menu_type")
    private Integer menuType;

    /**
     * 是否隐藏：0-否；1-是
     */
    @TableField("hidden")
    private Integer hidden;

    /**
     * 菜单状态：0-不可用；1-可用
     */
    @TableField("status")
    private Integer status;

    /**
     * 权限标识
     */
    @TableField("perms")
    private String perms;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    @TableField("create_by")
    private Long createBy;

    @TableField("update_by")
    private Long updateBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    /**
     * 描述信息
     */
    @TableField("remark")
    private String remark;

}
