package com.xiaozhi.aoaojiao.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author xiaozhi
 */
@Data
public class SysUserAddOrUpdateDTO {
        
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别：0-保密；1-男；2-女
     */
    private Integer sex;

    /**
     * 状态：0-禁用；1-启用
     */
    private Integer status;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String phoneNumber;


    /**
     * 删除标记：0-存在；1-删除
     */
    private Integer delFlag;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 描述
     */
    private String remark;

    /**
     * 部门ID
     */
    private Long deptId;

    private List<Long> roleIds;
}
