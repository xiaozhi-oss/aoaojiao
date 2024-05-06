package com.xiaozhi.aoaojiao.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 10:20:23
 */
@TableName("sys_user")
@Data
public class SysUser {

    /**
     * 主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 性别：0-保密；1-男；2-女
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 状态：0-禁用；1-启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 头像url
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 手机号码
     */
    @TableField("phone_number")
    private String phoneNumber;


    /**
     * 删除标记：0-存在；1-删除
     */
    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;

    /**
     * 创建者
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 更新者
     */
    @TableField("update_by")
    private Long updateBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 描述
     */
    @TableField("remark")
    private String remark;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;

    @TableField(exist = false)
    private List<SysRole> roles;

    @TableField(exist = false)
    private SysDept dept;
}
