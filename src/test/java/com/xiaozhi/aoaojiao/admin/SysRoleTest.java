package com.xiaozhi.aoaojiao.admin;

import com.xiaozhi.aoaojiao.mapper.SysRoleMapper;
import com.xiaozhi.aoaojiao.model.dto.SysRoleListDTO;
import com.xiaozhi.aoaojiao.model.entity.SysRole;
import com.xiaozhi.aoaojiao.model.vo.SysRoleVO;
import com.xiaozhi.aoaojiao.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author xiaozhi
 */
@SpringBootTest
public class SysRoleTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void testSelectRoleList(){
        List<SysRole> sysRoles = sysRoleMapper.selectRoleList(new SysRole());
        sysRoles.forEach(System.out::println);
    }

    @Test
    public void testSelectRoleList02(){
        List<SysRoleVO> sysRoleVOS = sysRoleService.getRoleList(new SysRoleListDTO());
        sysRoleVOS.forEach(System.out::println);
    }
}
