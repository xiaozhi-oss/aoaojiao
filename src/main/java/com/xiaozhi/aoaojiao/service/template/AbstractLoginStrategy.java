package com.xiaozhi.aoaojiao.service.template;

import com.xiaozhi.aoaojiao.core.constants.RedisConstants;
import com.xiaozhi.aoaojiao.core.utils.JwtTokenUtil;
import com.xiaozhi.aoaojiao.core.utils.RedisUtil;
import com.xiaozhi.aoaojiao.mapper.SysUserMapper;
import com.xiaozhi.aoaojiao.model.dto.login.SysUserLoginDTO;
import com.xiaozhi.aoaojiao.model.entity.SysUser;
import com.xiaozhi.aoaojiao.model.vo.LoginUserVO;
import com.xiaozhi.aoaojiao.model.vo.SysUserLoginVO;
import com.xiaozhi.aoaojiao.service.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaozhi
 * <p>
 * 登录策略模板
 */
@Component
public abstract class AbstractLoginStrategy implements LoginStrategy {

    @Autowired
    public RedisUtil redisUtil;

    @Autowired
    public JwtTokenUtil jwtTokenUtil;

    @Autowired
    public SysUserMapper sysUserMapper;

    @Override
    public SysUserLoginVO login(SysUserLoginDTO sysUserLoginDTO) {
        // 1 认证
        SysUser sysUser = authentication(sysUserLoginDTO);
        // 2 颁发令牌
        var sysUserLoginVO = new SysUserLoginVO();
        var token = jwtTokenUtil.createToken(sysUser.getUsername());
        sysUserLoginVO.setAccessToken(token);
        // 创建登录用户对象，存放登录信息 -> 后续可扩展强制退出操作
        LoginUserVO loginUserVo = new LoginUserVO(sysUser.getUserId(), sysUser.getDeptId(), token, sysUser, null);
        redisUtil.set(RedisConstants.getLoginTokenKey(token), loginUserVo, jwtTokenUtil.getExpireTime());
        return sysUserLoginVO;
    }

    public abstract SysUser authentication(SysUserLoginDTO sysUserLoginDTO);
}
