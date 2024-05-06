package com.xiaozhi.aoaojiao.service.strategy.impl;

import com.xiaozhi.aoaojiao.core.enums.LoginType;
import com.xiaozhi.aoaojiao.model.dto.login.SysUserLoginDTO;
import com.xiaozhi.aoaojiao.model.entity.SysUser;
import com.xiaozhi.aoaojiao.service.template.AbstractLoginStrategy;
import org.springframework.stereotype.Component;

/**
 * @author xiaozhi
 *
 * 用户名密码登录策略
 */
@Component
public class PhoneNumberLoginStrategy extends AbstractLoginStrategy {

    @Override
    public SysUser authentication(SysUserLoginDTO sysUserLoginDTO) {
        return null;
    }

    @Override
    public boolean supports(LoginType loginType) {
        return loginType.equals(LoginType.PHONE_NUMBER);
    }
}
