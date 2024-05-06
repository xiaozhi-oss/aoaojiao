package com.xiaozhi.aoaojiao.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@TableName("sys_user")
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
    @TableField("avater")
    private String avater;

    /**
     * 手机号码
     */
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 用户类型：0-系统用户；1-普通用户
     */
    @TableField("user_type")
    private Integer userType;

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


}
