package com.xiaozhi.aoaojiao.service.serviceImpl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.aoaojiao.mapper.SysRoleMapper;
import com.xiaozhi.aoaojiao.model.dto.SysRoleAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysRoleListDTO;
import com.xiaozhi.aoaojiao.model.entity.SysMenu;
import com.xiaozhi.aoaojiao.model.entity.SysRole;
import com.xiaozhi.aoaojiao.model.vo.SysRoleVO;
import com.xiaozhi.aoaojiao.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRoleVO> getRoleList(SysRoleListDTO sysRoleListDTO) {
        var sysRole = BeanUtil.copyProperties(sysRoleListDTO, SysRole.class);
        var sysRoles = sysRoleMapper.selectRoleList(sysRole);
        var sysRoleVOList = new ArrayList<SysRoleVO>();
        for (SysRole role : sysRoles) {
            var list = role.getMenus().stream().map(SysMenu::getMenuId).toList();
            var sysRoleVO = BeanUtil.copyProperties(role, SysRoleVO.class);
            sysRoleVO.setMenuIds(list);
            sysRoleVOList.add(sysRoleVO);
        }
        return sysRoleVOList;
    }

    @Override
    public void deleteRoleByIds(List<Long> ids) {

    }

    @Override
    public void addOrUpdateRole(SysRoleAddOrUpdateDTO sysRoleAddOrUpdateDTO) {

    }
}
