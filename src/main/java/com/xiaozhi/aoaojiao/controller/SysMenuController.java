package com.xiaozhi.aoaojiao.controller;


import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import com.xiaozhi.aoaojiao.model.dto.SysMenuAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysMenuListDTO;
import com.xiaozhi.aoaojiao.model.vo.SysMenuVO;
import com.xiaozhi.aoaojiao.model.vo.SysTreeMenuVO;
import com.xiaozhi.aoaojiao.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  菜单 controller
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 03:56:45
 */
@Tag(name = "菜单相关接口")
@RestController
@RequestMapping("/api/menu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @Operation(summary = "添加菜单")
    @PostMapping("/save")
    public ResponseResult<Boolean> saveMenu(@RequestBody @Valid SysMenuAddOrUpdateDTO sysMenuAddOrUpdateDTO) {
        sysMenuService.addOrUpdateMenu(sysMenuAddOrUpdateDTO);
        return ResponseResult.success();
    }

    @Operation(summary = "更新菜单")
    @PutMapping("/update")
    public ResponseResult<Boolean> updateMenu(@RequestBody @Valid SysMenuAddOrUpdateDTO sysMenuAddOrUpdateDTO) {
        sysMenuService.addOrUpdateMenu(sysMenuAddOrUpdateDTO);
        return ResponseResult.success();
    }

    @Parameters({
            @Parameter(name = "deptName", description = "部门名称", in = ParameterIn.QUERY),
            @Parameter(name = "status", description = "部门状态", in = ParameterIn.QUERY),
    })
    @Operation(summary = "获取菜单列表")
    @GetMapping("/list")
    public ResponseResult<List<SysMenuVO>> getMenuList(@Valid SysMenuListDTO sysMenuListDTO) {
        return ResponseResult.success(sysMenuService.selectMenuList(sysMenuListDTO));
    }

    @Operation(summary = "获取菜单树状列表")
    @GetMapping("/treeList")
    public ResponseResult<List<SysTreeMenuVO>> getTreeMenuList() {
        return ResponseResult.success(sysMenuService.selectMenuTreeList());
    }

    @Parameters(
            @Parameter(name = "deptId", description = "部门ID", in = ParameterIn.QUERY)
    )
    @Operation(summary = "根据ID删除菜单")
    @DeleteMapping("/deleteById")
    public ResponseResult<Void> deleteMenuById(@RequestBody Long deptId) {
        sysMenuService.deleteMenuById(deptId);
        return ResponseResult.success();
    }
}

