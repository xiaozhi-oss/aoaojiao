package com.xiaozhi.aoaojiao.service.strategy.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaozhi.aoaojiao.core.constants.RedisConstants;
import com.xiaozhi.aoaojiao.core.enums.LoginType;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.model.dto.login.EmailLoginDTO;
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
public class EmailLoginStrategy extends AbstractLoginStrategy {

    @Override
    public SysUser authentication(SysUserLoginDTO sysUserLoginDTO) {
        var el = (EmailLoginDTO) sysUserLoginDTO;
        SysUser sysUser = checkEmailExist(el.getEmail());
        // 判断验证码是否正确
        String cacheCode = (String) redisUtil.get(RedisConstants.getLoginCodeKey(el.getEmail()));
        Assert.equals(cacheCode, el.getCode(),
                () -> BusinessException.build(ResponseStatus.LOGIN_CODE_ERROR));
        return sysUser;
    }

    /**
     * 校验邮箱是否存在系统用户中，不存在需要联系管理员添加或自行添加邮箱登录方式
     * @param email 邮箱
     * @return  用户对象
     */
    private SysUser checkEmailExist(String email) {
        var wrapper = new LambdaQueryWrapper<SysUser>();
        wrapper.eq(SysUser::getEmail, email);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        if (ObjectUtil.isEmpty(sysUser)) {
            throw BusinessException.build(ResponseStatus.LOGIN_USER_NOT_EXIST_ERROR);
        }
        return sysUser;
    }

    @Override
    public boolean supports(LoginType loginType) {
        return loginType.equals(LoginType.EMAIL);
    }
}
