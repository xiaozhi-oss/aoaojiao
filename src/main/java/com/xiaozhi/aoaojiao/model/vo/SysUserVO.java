package com.xiaozhi.aoaojiao.model.vo;

import com.xiaozhi.aoaojiao.model.entity.SysDept;
import lombok.Data;

import java.util.List;

/**
 * @author xiaozhi
 */
@Data
public class SysUserVO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 头像url
     */
    private String avatar;

    private String status;

    /**
     * 角色ID列表
     */
    private List<Long> roleIds;

    private SysDept dept;
}
