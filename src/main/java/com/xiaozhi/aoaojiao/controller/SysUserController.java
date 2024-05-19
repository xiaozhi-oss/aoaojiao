package com.xiaozhi.aoaojiao.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import com.xiaozhi.aoaojiao.model.dto.SysUserAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysUserListDTO;
import com.xiaozhi.aoaojiao.model.vo.SysUserVO;
import com.xiaozhi.aoaojiao.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统用户 controller
 *  
 * @author xiaozhi
 */
@Tag(name = "用户相关接口")
@RestController
@RequestMapping("/api/user")
public class SysUserController {
    
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/list")
    @Operation(summary = "获取系统用户列表")
    public ResponseResult<IPage<SysUserVO>> getUserList(@Valid SysUserListDTO sysUserListDTO) {
        return ResponseResult.success(sysUserService.getSysUserList(sysUserListDTO));
    }
    
    @Operation(summary = "添加用户")
    @PostMapping("/save")
    public ResponseResult<Void> saveSysUser(@RequestBody @Valid SysUserAddOrUpdateDTO sysUserAddOrUpdateDTO) {
        sysUserService.checkSysUser(sysUserAddOrUpdateDTO);
        sysUserService.saveSysUser(sysUserAddOrUpdateDTO);
        return ResponseResult.success();
    }

    @Operation(summary = "修改用户")
    @PutMapping("/update")
    public ResponseResult<Void> updateSysRole(@RequestBody @Valid SysUserAddOrUpdateDTO sysUserAddOrUpdateDTO) {
        sysUserService.checkSysUser(sysUserAddOrUpdateDTO);
        sysUserService.updateSysUser(sysUserAddOrUpdateDTO);
        return ResponseResult.success();
    }

    @Operation(summary = "删除单个或多个用户")
    @DeleteMapping("/deleteByIds")
    public ResponseResult<Void> deleteSysRoleByIds(@RequestBody List<Long> ids) {
        return ResponseResult.success();
    }
}
