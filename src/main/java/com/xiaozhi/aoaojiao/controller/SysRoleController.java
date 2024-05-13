package com.xiaozhi.aoaojiao.controller;


import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import com.xiaozhi.aoaojiao.model.dto.SysRoleAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysRoleListDTO;
import com.xiaozhi.aoaojiao.model.vo.SysRoleVO;
import com.xiaozhi.aoaojiao.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Operation(summary = "获取角色列表")
    @GetMapping("/list")
    public ResponseResult<List<SysRoleVO>> getSysRoleList(SysRoleListDTO sysRoleListDTO) {
        return ResponseResult.success(sysRoleService.getRoleList(sysRoleListDTO));
    }

    @Operation(summary = "添加或菜单")
    @PostMapping("/saveOrUpdate")
    public ResponseResult<Boolean> saveOrUpdateRole(@RequestBody @Valid SysRoleAddOrUpdateDTO sysRoleAddOrUpdateDTO) {
        sysRoleService.addOrUpdateRole(sysRoleAddOrUpdateDTO);
        return ResponseResult.success();
    }
}

