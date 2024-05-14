package com.xiaozhi.aoaojiao.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author xiaozhi
 */
@Data
public class SysRoleAddOrUpdateDTO {

    /**
     * 主键ID
     */
    private Long roleId;

    /**
     * 角色名
     */
    @NotBlank(message = "角色名不能为空")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @NotBlank(message = "权限字符不能为空")
    private String roleStr;

    /**
     * 角色排序值，根据此排序值进行排序展示
     */
    // @TableField("role_sort")
    // private Integer roleSort;

    /**
     * 状态：0-禁用；1-启用
     */
    @Min(value = 0, message = "状态最小值为 0")
    @Max(value = 1, message = "状态最大值为 1")
    private Integer status;

    /**
     * 描述
     */
    private String remark;

    /**
     * 菜单组
     */
    private List<Long> menuIds;
}
