package com.xiaozhi.aoaojiao.model.dto.login;

import com.xiaozhi.aoaojiao.core.enums.LoginType;

/**
 * @author xiaozhi
 *
 * 系统用户登录DTO的抽象
 */
public interface SysUserLoginDTO {

    /**
     * 获取登录类型 -> 通过它来做一个匹配
     * @return 返回登录类型
     */
    LoginType getLoginType();
}
