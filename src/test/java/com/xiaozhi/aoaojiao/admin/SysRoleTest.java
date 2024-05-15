package com.xiaozhi.aoaojiao.admin;

import com.xiaozhi.aoaojiao.mapper.SysRoleMapper;
import com.xiaozhi.aoaojiao.model.dto.SysRoleListDTO;
import com.xiaozhi.aoaojiao.model.entity.SysRole;
import com.xiaozhi.aoaojiao.model.vo.SysRoleVO;
import com.xiaozhi.aoaojiao.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
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

    @Test
    public void testBatchInsertRoleMenu(){
        var menuIds = new ArrayList<Long>();
        menuIds.add(6L);
        menuIds.add(7L);
        sysRoleMapper.batchInsertRoleMenu(1L, menuIds);
    }

    @Test
    public void testBatchDeleteRoleMenuByRoleIds(){
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(3L);
        roleIds.add(4L);
        int count = sysRoleMapper.batchDeleteRoleMenuByRoleIds(roleIds);
        System.out.println(count);
    }
}
