package com.xiaozhi.aoaojiao.core.enums;

import com.xiaozhi.aoaojiao.core.exception.BusinessException;

/**
 * @author xiaozhi
 *
 * 登录类型
 */
public enum LoginType {

    USERNAME_PWD,
    EMAIL,
    PHONE_NUMBER;

    /**
     * 替换原本的 valueOf 方法，自定义异常
     * @param loginType 登录类型
     * @return  返回登录类型
     */
    public static LoginType parseLoginType(String loginType) {
        try {
            return LoginType.valueOf(loginType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ResponseStatus.LOGIN_TYPE_ERROR);
        }
    }

}
