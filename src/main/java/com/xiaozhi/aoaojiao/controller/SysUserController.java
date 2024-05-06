package com.xiaozhi.aoaojiao.controller;

import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import com.xiaozhi.aoaojiao.model.entity.SysUser;
import com.xiaozhi.aoaojiao.service.SysUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  用户 controller
 * </p>
 *
 * @author xiaozhi
 */
@Tag(name = "用户相关接口")
@RestController
@RequestMapping("/api/user")
public class SysUserController {
    
    @Resource
    private SysUserService sysUserService;

    @GetMapping("/all")
    public ResponseResult<Void> selectAllUser() {
        List<SysUser> list = sysUserService.list();
        list.forEach(System.out::println);
        return ResponseResult.success();
    }
}
