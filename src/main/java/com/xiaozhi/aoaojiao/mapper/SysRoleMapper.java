package com.xiaozhi.aoaojiao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaozhi.aoaojiao.model.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

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
}
