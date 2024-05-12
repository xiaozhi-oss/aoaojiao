package com.xiaozhi.aoaojiao.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "菜单名不能为空")
    private String menuName;

    /**
     * 父菜单ID，表示最顶层菜单
     */
    private Long parentId;

    private String path;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 是否为外链：0-不是；1-是
     */
    @NotNull(message = "是否为外链选项不能为空")
    @Max(value = 1, message = "最大值为 1")
    @Min(value = 0, message = "最小值为 0")
    private Integer isOuterChain;

    /**
     * 菜单类型：0-目录；1-菜单；2-按钮
     */
    @NotNull(message = "菜单类型不能为空")
    @Max(value = 2, message = "最大值为 2")
    @Min(value = 0, message = "最小值为 0")
    private Integer menuType;

    /**
     * 是否隐藏：0-否；1-是
     */
    @NotNull(message = "是否隐藏选项不能为空")
    @Max(value = 1, message = "最大值为 1")
    @Min(value = 0, message = "最小值为 0")
    private Integer hidden;

    /**
     * 菜单状态：0-不可用；1-可用
     */
    @NotNull(message = "菜单状态不能为空")
    @Max(value = 1, message = "最大值为 1")
    @Min(value = 0, message = "最小值为 0")
    private Integer status;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 描述信息
     */
    private String remark;

    /**
     * 描述信息
     */
    @JsonIgnore
    private Date createTime;
}
