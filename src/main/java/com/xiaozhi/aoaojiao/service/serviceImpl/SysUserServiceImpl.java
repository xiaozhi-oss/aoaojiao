package com.xiaozhi.aoaojiao.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.core.utils.SecurityUtil;
import com.xiaozhi.aoaojiao.mapper.SysUserMapper;
import com.xiaozhi.aoaojiao.model.dto.SysUserAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysUserListDTO;
import com.xiaozhi.aoaojiao.model.dto.login.SysUserLoginDTO;
import com.xiaozhi.aoaojiao.model.entity.SysUser;
import com.xiaozhi.aoaojiao.model.vo.LoginUserVO;
import com.xiaozhi.aoaojiao.model.vo.SysUserLoginVO;
import com.xiaozhi.aoaojiao.model.vo.SysUserVO;
import com.xiaozhi.aoaojiao.service.SysUserService;
import com.xiaozhi.aoaojiao.service.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUserLoginVO login(SysUserLoginDTO sysUserLoginDTO) {
        // 匹配对应的登录方式
        return loginStrategyList.stream()
                .filter(loginStrategy -> loginStrategy.supports(sysUserLoginDTO.getLoginType()))
                .findFirst()
                .map(loginStrategy -> loginStrategy.login(sysUserLoginDTO))
                .orElseThrow(BusinessException.buildError(ResponseStatus.LOGIN_TYPE_ERROR));
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
        Page<SysUser> page = Page.of(sysUserListDTO.getPageNo(), sysUserListDTO.getPageSize());
        IPage<SysUser> sysUserIPage = sysUserMapper.selectSysUser(page, sysUser);
        List<SysUserVO> sysUserVOList = sysUserIPage.getRecords().stream()
                .map(sysUserVO -> BeanUtil.copyProperties(sysUserVO, SysUserVO.class))
                .toList();
        Page<SysUserVO> sysUserVOPage = Page.of(page.getCurrent(), page.getSize(), sysUserIPage.getTotal());
        sysUserVOPage.setRecords(sysUserVOList);
        return sysUserVOPage;
    }

    @Transactional
    @Override
    public void saveSysUser(SysUserAddOrUpdateDTO sysUserAddOrUpdateDTO) {
        var sysUser = BeanUtil.copyProperties(sysUserAddOrUpdateDTO, SysUser.class);
        sysUser.setUsername(sysUser.getUsername().trim());
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        sysUser.setCreateBy(SecurityUtil.getLoginUserId());
        sysUser.setCreateTime(DateTime.now());
        int count = sysUserMapper.insert(sysUser);
        Assert.isTrue(count > 0,
                BusinessException.buildError(ResponseStatus.OPERATION_ERROR));
        // 不为空才添加
        if (!sysUser.getRoleIds().isEmpty()) {
            addSysUserAndSysRoleMap(sysUser.getUserId(), sysUser.getRoleIds());
        }
    }

    @Transactional
    @Override
    public void updateSysUser(SysUserAddOrUpdateDTO sysUserAddOrUpdateDTO) {
        var sysUser = BeanUtil.copyProperties(sysUserAddOrUpdateDTO, SysUser.class);
        sysUser.setUsername(sysUser.getUsername().trim());
        sysUser.setUpdateBy(SecurityUtil.getLoginUserId());
        sysUser.setUpdateTime(DateTime.now());
        int count = sysUserMapper.updateById(sysUser);
        Assert.isTrue(count > 0,
                BusinessException.buildError(ResponseStatus.OPERATION_ERROR));
        addSysUserAndSysRoleMap(sysUser.getUserId(), sysUser.getRoleIds());
    }

    private void addSysUserAndSysRoleMap(Long userId, List<Long> roleIds) {
        int deleteCount = sysUserMapper.deleteUserAndRoleByUserId(userId);
        var insertCount = 0;
        if (!roleIds.isEmpty()) {
            insertCount = sysUserMapper.batchInsertUserRole(userId, roleIds);
        }
        Assert.isTrue(deleteCount >= 0 && insertCount >= 0,
                BusinessException.buildError(ResponseStatus.OPERATION_ERROR));
    }
    
    @Transactional
    @Override
    public void deleteUserByIds(List<Long> ids) {
        int deleteCount = sysUserMapper.deleteBatchIds(ids);
        Assert.isTrue(deleteCount > 0,
                () -> BusinessException.build(ResponseStatus.OPERATION_ERROR));
        int count = sysUserMapper.batchDeleteUserRoleByUserIds(ids);
        Assert.isTrue(count >= 0, 
                BusinessException.buildError(ResponseStatus.OPERATION_ERROR));
    }

    @Override
    public void checkSysUser(SysUserAddOrUpdateDTO sysUserAddOrUpdateDTO) {
        var wrapper = new LambdaQueryWrapper<SysUser>();
        wrapper.eq(SysUser::getUsername, sysUserAddOrUpdateDTO.getUsername());
        Long count = sysUserMapper.selectCount(wrapper);
        Assert.isTrue(count <= 0,
                BusinessException.buildError(ResponseStatus.NAME_REPEAT_ERROR));
    }
}
