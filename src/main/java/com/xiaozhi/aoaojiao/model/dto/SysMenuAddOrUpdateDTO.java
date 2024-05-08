package com.xiaozhi.aoaojiao.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author xiaozhi
 */
@Data
public class SysMenuAddOrUpdateDTO {

    private Long menuId;

    /**
     * 菜单名字
     */
    private String menuName;

    /**
     * 父菜单ID，表示最顶层菜单
     */
    private Long parentId;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 是否为外链：0-不是；1-是
     */
    private Integer isOuterChain;

    /**
     * 菜单类型：0-目录；1-菜单；2-按钮
     */
    private Integer menuType;

    /**
     * 是否隐藏：0-否；1-是
     */
    private Integer hidden;

    /**
     * 菜单状态：0-不可用；1-可用
     */
    private Integer menuStatus;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    /**
     * 描述信息
     */
    private String remark;
}
