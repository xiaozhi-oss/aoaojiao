package com.xiaozhi.aoaojiao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaozhi.aoaojiao.model.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 03:56:45
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectRoleList(SysRole sysRole);

    /**
     * 批量插入 role 和 menu 的关联表
     * @param roleId    角色ID
     * @param menuIds   菜单ID列表
     * @return  返回插入条数
     */
    int batchInsertRoleMenu(@Param("roleId") Long roleId,
                            @Param("menuIds") List<Long> menuIds);

    /**
     * 根据角色ID删除关联
     * @param roleId    角色ID
     * @return  返回插入条数
     */
    int deleteRoleMenuByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色ID列表批量删除关联
     * @param roleIds
     * @return
     */
    int batchDeleteRoleMenuByRoleIds(@Param("roleIds") List<Long> roleIds);
}
