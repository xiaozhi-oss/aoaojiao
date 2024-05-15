package com.xiaozhi.aoaojiao.service.serviceImpl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.core.utils.SecurityUtil;
import com.xiaozhi.aoaojiao.mapper.SysRoleMapper;
import com.xiaozhi.aoaojiao.model.dto.SysRoleAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysRoleListDTO;
import com.xiaozhi.aoaojiao.model.entity.SysRole;
import com.xiaozhi.aoaojiao.model.vo.SysRoleVO;
import com.xiaozhi.aoaojiao.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRoleVO> getRoleList(SysRoleListDTO sysRoleListDTO) {
        var sysRole = BeanUtil.copyProperties(sysRoleListDTO, SysRole.class);
        var sysRoles = sysRoleMapper.selectRoleList(sysRole);
        return BeanUtil.copyToList(sysRoles, SysRoleVO.class);
    }

    @Transactional
    @Override
    public void deleteRoleByIds(List<Long> ids) {
        int batchCount = sysRoleMapper.deleteBatchIds(ids);
        sysRoleMapper.batchDeleteRoleMenuByRoleIds(ids);
        Assert.isTrue(batchCount > 0,
                () -> BusinessException.build(ResponseStatus.OPERATION_ERROR));
    }

    @Transactional
    @Override
    public void updateSysRole(SysRoleAddOrUpdateDTO sysRoleAddOrUpdateDTO) {
        var sysRole = BeanUtil.copyProperties(sysRoleAddOrUpdateDTO, SysRole.class);
        sysRole.setCreateTime(new Date());
        sysRole.setCreateBy(SecurityUtil.getLoginUserId());
        int count = sysRoleMapper.updateById(sysRole);
        Assert.isTrue(count > 0,
                () -> BusinessException.build(ResponseStatus.OPERATION_ERROR));
        // 删除掉再重新插入
        sysRoleMapper.deleteRoleMenuByRoleId(sysRoleAddOrUpdateDTO.getRoleId());
        sysRoleMapper.batchInsertRoleMenu(sysRole.getRoleId(), sysRole.getMenuIds());
    }

    @Transactional
    @Override
    public void saveSysRole(SysRoleAddOrUpdateDTO sysRoleAddOrUpdateDTO) {
        var sysRole = BeanUtil.copyProperties(sysRoleAddOrUpdateDTO, SysRole.class);
        sysRole.setCreateTime(new Date());
        sysRole.setCreateBy(SecurityUtil.getLoginUserId());
        int count = sysRoleMapper.insert(sysRole);
        Assert.isTrue(count > 0,
                () -> BusinessException.build(ResponseStatus.OPERATION_ERROR));
        sysRoleMapper.batchInsertRoleMenu(sysRole.getRoleId(), sysRole.getMenuIds());
    }

    /**
     * 检查名字是否重复
     */
    @Override
    public void checkSysRoleNameRepeat(SysRoleAddOrUpdateDTO sysRoleAddOrUpdateDTO) {
        var wrapper = new LambdaQueryWrapper<SysRole>();
        wrapper.eq(SysRole::getRoleName, sysRoleAddOrUpdateDTO.getRoleName());
        Long count = sysRoleMapper.selectCount(wrapper);
        Assert.isTrue(count <= 0,
                () -> BusinessException.build(ResponseStatus.NAME_REPEAT_ERROR));
    }
}
