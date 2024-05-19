package com.xiaozhi.aoaojiao.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhi.aoaojiao.mapper.SysUserMapper;
import com.xiaozhi.aoaojiao.model.entity.SysUser;
import com.xiaozhi.aoaojiao.service.SysUserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @author xiaozhi
 */
@SpringBootTest
public class SysUserTest {
    
    @Resource
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testGetUserList() {
        List<SysUser> list = sysUserService.list();
        list.forEach(System.out::println);
    }


    @Test
    void test() {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("sys_test");
        IPage<SysUser> sysUserIPage = sysUserMapper.selectSysUser(new Page<>(1, 10), sysUser);
        System.out.println(sysUserIPage.getRecords());
    }

    @Test
    public void test2(){
        var sysUsers = sysUserMapper.selectList(null);
        sysUsers.forEach(System.out::println);
    }
}
