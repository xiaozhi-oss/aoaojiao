package com.xiaozhi.aoaojiao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaozhi.aoaojiao.model.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 03:56:45
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户
     * @param sysUser  用户
     * @return  用户
     */
    IPage<SysUser> selectSysUser(IPage<SysUser> page, @Param("sysUser") SysUser sysUser);
}
