package com.xiaozhi.aoaojiao.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import com.xiaozhi.aoaojiao.model.dto.SysRoleAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysUserListDTO;
import com.xiaozhi.aoaojiao.model.vo.SysUserVO;
import com.xiaozhi.aoaojiao.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
public class SysUserController {
    
    private SysUserService sysUserService;

    @GetMapping("/list")
    @Operation(summary = "获取系统用户列表")
    public ResponseResult<IPage<SysUserVO>> getUserList(@Valid SysUserListDTO sysUserListDTO) {
        return ResponseResult.success(sysUserService.getSysUserList(sysUserListDTO));
    }
    
    @Operation(summary = "添加角色")
    @PostMapping("/save")
    public ResponseResult<Void> saveSysRole(@RequestBody @Valid SysRoleAddOrUpdateDTO sysRoleAddOrUpdateDTO) {
        return ResponseResult.success();
    }

    @Operation(summary = "修改角色")
    @PutMapping("/update")
    public ResponseResult<Void> updateSysRole(@RequestBody @Valid SysRoleAddOrUpdateDTO sysRoleAddOrUpdateDTO) {
        return ResponseResult.success();
    }

    @Operation(summary = "删除单个或多个角色")
    @DeleteMapping("/deleteByIds")
    public ResponseResult<Void> deleteSysRoleByIds(@RequestBody List<Long> roleIds) {
        return ResponseResult.success();
    }
}
