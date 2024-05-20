package com.xiaozhi.aoaojiao.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author xiaozhi
 */
@Data
public class SysRoleVO {

    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;

    private String roleStr;

    /**
     * 状态：0-禁用；1-启用
     */
    private Integer status;

    private Date createTime;

    private String remark;

    private List<Long> menuIds;
}
