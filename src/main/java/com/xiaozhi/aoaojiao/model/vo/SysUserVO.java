package com.xiaozhi.aoaojiao.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author xiaozhi
 */
@Data
public class SysUserVO {

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
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 头像url
     */
    @TableField("avatar")
    private String avatar;

}
