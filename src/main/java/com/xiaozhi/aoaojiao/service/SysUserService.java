package com.xiaozhi.aoaojiao.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhi.aoaojiao.model.dto.SysUserAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysUserListDTO;
import com.xiaozhi.aoaojiao.model.dto.login.SysUserLoginDTO;
import com.xiaozhi.aoaojiao.model.entity.SysUser;
import com.xiaozhi.aoaojiao.model.vo.SysUserLoginVO;
import com.xiaozhi.aoaojiao.model.vo.SysUserVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 03:56:45
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 登录接口
     * @return 响应对象
     */
    SysUserLoginVO login(SysUserLoginDTO sysUserLoginDTO);

    SysUserVO getUserInfo();

    IPage<SysUserVO> getSysUserList(SysUserListDTO sysUserListDTO);

    /**
     * 添加角色
     * @param sysUserAddOrUpdateDTO 用户添加或修改 DTO
     */
    void saveSysUser(SysUserAddOrUpdateDTO sysUserAddOrUpdateDTO);

    /**
     * 修改角色
     * @param sysUserAddOrUpdateDTO 角色添加或修改 DTO
     */
    void updateSysUser(SysUserAddOrUpdateDTO sysUserAddOrUpdateDTO);


    /**
     * 根据ID批量或单个删除
     */
    void deleteUserByIds(List<Long> ids);

    /**
     * 检查名字是否已重复
     * @param sysUserAddOrUpdateDTO 角色添加或修改 DTO
     */
    void checkSysUser(SysUserAddOrUpdateDTO sysUserAddOrUpdateDTO);
}
