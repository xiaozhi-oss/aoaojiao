package com.xiaozhi.aoaojiao.mapper;

import com.xiaozhi.aoaojiao.model.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
     * 根据用户名查询
     * @param username  用户名
     * @return  用户
     */
    SysUser selectSysUserByUsername(String username);
}
