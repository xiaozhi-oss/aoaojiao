package com.xiaozhi.aoaojiao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaozhi.aoaojiao.model.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 03:56:45
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户
     *
     * @param sysUser 用户
     * @return 用户
     */
    IPage<SysUser> selectSysUser(IPage<SysUser> page, @Param("sysUser") SysUser sysUser);

    /**
     * 批量插入 user 和 role 的关联表
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     * @return 返回插入条数
     */
    int batchInsertUserRole(@Param("userId") Long userId,
                            @Param("roleIds") List<Long> roleIds);

    /**
     * 根据用户ID删除关联
     * @param userId    用户ID
     * @return      返回插入条数
     */
    int deleteUserAndRoleByUserId(@Param("userId") Long userId);
    
    /**
     * 根据角色ID列表批量删除关联
     *
     * @param userIds
     * @return
     */
    int batchDeleteUserRoleByUserIds(@Param("userIds") List<Long> userIds);
}
