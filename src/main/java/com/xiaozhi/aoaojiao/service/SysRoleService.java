package com.xiaozhi.aoaojiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhi.aoaojiao.model.dto.SysRoleAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysRoleListDTO;
import com.xiaozhi.aoaojiao.model.entity.SysRole;
import com.xiaozhi.aoaojiao.model.vo.SysRoleVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 03:56:45
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取角色列表
     * @param sysRoleListDTO    角色列表 DTO
     * @return 角色列表
     */
    List<SysRoleVO> getRoleList(SysRoleListDTO sysRoleListDTO);

    /**
     * 添加角色
     * @param sysRoleAddOrUpdateDTO 角色添加或修改 DTO
     */
    void saveSysRole(SysRoleAddOrUpdateDTO sysRoleAddOrUpdateDTO);

    /**
     * 修改角色
     * @param sysRoleAddOrUpdateDTO 角色添加或修改 DTO
     */
    void updateSysRole(SysRoleAddOrUpdateDTO sysRoleAddOrUpdateDTO);


    /**
     * 根据ID批量或单个删除
     */
    void deleteRoleByIds(List<Long> ids);

    /**
     * 检查名字是否已重复
     * @param sysRoleAddOrUpdateDTO 角色添加或修改 DTO
     */
    void checkSysRoleNameRepeat(SysRoleAddOrUpdateDTO sysRoleAddOrUpdateDTO);
}
