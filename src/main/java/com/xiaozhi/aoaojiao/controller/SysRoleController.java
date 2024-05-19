package com.xiaozhi.aoaojiao.controller;


import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import com.xiaozhi.aoaojiao.model.dto.SysRoleAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysRoleListDTO;
import com.xiaozhi.aoaojiao.model.vo.SysRoleVO;
import com.xiaozhi.aoaojiao.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 03:56:45
 */
@Tag(name = "角色相关接口")
@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
public class SysRoleController {
    
    private SysRoleService sysRoleService;

    @Operation(summary = "获取角色列表")
    @GetMapping("/list")
    public ResponseResult<List<SysRoleVO>> getSysRoleList(SysRoleListDTO sysRoleListDTO) {
        return ResponseResult.success(sysRoleService.getRoleList(sysRoleListDTO));
    }

    @Operation(summary = "添加角色")
    @PostMapping("/save")
    public ResponseResult<Void> saveSysRole(@RequestBody @Valid SysRoleAddOrUpdateDTO sysRoleAddOrUpdateDTO) {
        sysRoleService.saveSysRole(sysRoleAddOrUpdateDTO);
        return ResponseResult.success();
    }

    @Operation(summary = "修改角色")
    @PutMapping("/update")
    public ResponseResult<Void> updateSysRole(@RequestBody @Valid SysRoleAddOrUpdateDTO sysRoleAddOrUpdateDTO) {
        sysRoleService.updateSysRole(sysRoleAddOrUpdateDTO);
        return ResponseResult.success();
    }

    @Operation(summary = "删除单个或多个角色")
    @DeleteMapping("/deleteByIds")
    public ResponseResult<Void> deleteSysRoleByIds(@RequestBody List<Long> roleIds) {
        sysRoleService.deleteRoleByIds(roleIds);
        return ResponseResult.success();
    }
}

