package com.xiaozhi.aoaojiao.service.strategy.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaozhi.aoaojiao.core.constants.RedisConstants;
import com.xiaozhi.aoaojiao.core.enums.LoginType;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.mapper.SysUserMapper;
import com.xiaozhi.aoaojiao.model.dto.login.SysUserLoginDTO;
import com.xiaozhi.aoaojiao.model.dto.login.UserNamePasswordLoginDTO;
import com.xiaozhi.aoaojiao.model.entity.SysUser;
import com.xiaozhi.aoaojiao.service.template.AbstractLoginStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author xiaozhi
 * <p>
 * 用户名密码登录策略
 */
@Slf4j
@Component
public class UsernamePasswordLoginStrategy extends AbstractLoginStrategy {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUser authentication(SysUserLoginDTO sysUserLoginDTO) {
        var up = (UserNamePasswordLoginDTO) sysUserLoginDTO;
        // 验证码校验 -> 从 redis 中获取验证码
        String uuid = up.getUuid().trim();
        String cacheCode = (String) redisUtil.get(RedisConstants.getLoginCodeKey(uuid));
        Assert.equals(up.getVerificationCode(), cacheCode,
                () -> BusinessException.build(ResponseStatus.LOGIN_CODE_ERROR));
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, up.getUsername().trim());
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if (ObjectUtil.isEmpty(sysUser) ||
                !passwordEncoder.matches(up.getPassword(), sysUser.getPassword())) {
            throw BusinessException.build(ResponseStatus.LOGIN_UNAME_PWD_ERROR);
        }
        return sysUser;
    }

    @Override
    public boolean supports(LoginType loginType) {
        return loginType.equals(LoginType.USERNAME_PWD);
    }
}
