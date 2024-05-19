package com.xiaozhi.aoaojiao.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.mapper.SysUserMapper;
import com.xiaozhi.aoaojiao.model.dto.SysUserListDTO;
import com.xiaozhi.aoaojiao.model.dto.login.SysUserLoginDTO;
import com.xiaozhi.aoaojiao.model.entity.SysRole;
import com.xiaozhi.aoaojiao.model.entity.SysUser;
import com.xiaozhi.aoaojiao.model.vo.LoginUserVO;
import com.xiaozhi.aoaojiao.model.vo.SysUserLoginVO;
import com.xiaozhi.aoaojiao.model.vo.SysUserVO;
import com.xiaozhi.aoaojiao.service.SysUserService;
import com.xiaozhi.aoaojiao.service.strategy.LoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    
    private List<LoginStrategy> loginStrategyList;
    
    private SysUserMapper sysUserMapper;

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
    public SysUserVO getUserInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var loginUserVo = (LoginUserVO) authentication.getPrincipal();
        return BeanUtil.copyProperties(loginUserVo.getSysUser(), SysUserVO.class);
    }

    @Override
    public IPage<SysUserVO> getSysUserList(SysUserListDTO sysUserListDTO) {
        SysUser sysUser = BeanUtil.copyProperties(sysUserListDTO, SysUser.class);
        Page<SysUser> page = new Page<>(sysUserListDTO.getPageNo(), sysUserListDTO.getPageSize());
        IPage<SysUser> sysUserIPage = sysUserMapper.selectSysUser(page, sysUser);
        List<SysUserVO> sysUserVOS = new ArrayList<>();
        sysUserIPage.getRecords().forEach(sUser -> {
            SysUserVO sysUserVO = BeanUtil.copyProperties(sUser, SysUserVO.class);
            List<Long> roleIds = sUser.getRoles().stream().map(SysRole::getRoleId).toList();
            sysUserVO.setRoleIds(roleIds);
            sysUserVOS.add(sysUserVO);
        });
        Page<SysUserVO> sysUserVOPage = new Page<>();
        sysUserVOPage.setTotal(sysUserIPage.getTotal());
        sysUserVOPage.setRecords(sysUserVOS);
        return sysUserVOPage;
    }
}
