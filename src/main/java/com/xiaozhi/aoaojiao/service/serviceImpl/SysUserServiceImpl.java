package com.xiaozhi.aoaojiao.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.core.utils.RedisUtil;
import com.xiaozhi.aoaojiao.mapper.SysUserMapper;
import com.xiaozhi.aoaojiao.model.dto.login.SysUserLoginDTO;
import com.xiaozhi.aoaojiao.model.entity.SysUser;
import com.xiaozhi.aoaojiao.model.vo.LoginUserVo;
import com.xiaozhi.aoaojiao.model.vo.SysUserLoginVO;
import com.xiaozhi.aoaojiao.service.SysUserService;
import com.xiaozhi.aoaojiao.service.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 03:56:45
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private List<LoginStrategy> loginStrategyList;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public SysUserLoginVO login(SysUserLoginDTO sysUserLoginDTO) {
        // 匹配对应的登录方式
        return loginStrategyList.stream()
                .filter(loginStrategy -> loginStrategy.supports(sysUserLoginDTO.getLoginType()))
                .findFirst()
                .map(loginStrategy -> loginStrategy.login(sysUserLoginDTO))
                .orElseThrow(() -> new BusinessException(ResponseStatus.LOGIN_TYPE_ERROR));
    }

    @Override
    public SysUser getUserInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var loginUserVo = (LoginUserVo) authentication.getPrincipal();
        return loginUserVo.getSysUser();
    }
}
