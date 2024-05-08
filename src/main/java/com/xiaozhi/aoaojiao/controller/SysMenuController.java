package com.xiaozhi.aoaojiao.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import com.xiaozhi.aoaojiao.model.dto.SysMenuAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysMenuListDTO;
import com.xiaozhi.aoaojiao.model.entity.SysMenu;
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
        checkNameRepeat(sysMenuAddOrUpdateDTO.getMenuName());
        var sysMenu = BeanUtil.copyProperties(sysMenuAddOrUpdateDTO, SysMenu.class);
        sysMenu.setCreateTime(DateTime.now());
        // TODO 设置创建者
        sysMenu.setCreateBy(0L);
        return ResponseResult.success();
    }

    @Operation(summary = "更新菜单")
    @PutMapping("/update")
    public ResponseResult<Boolean> updateMenu(@RequestBody @Valid SysMenuAddOrUpdateDTO sysMenuAddOrUpdateDTO) {
        checkNameRepeat(sysMenuAddOrUpdateDTO.getMenuName());
        var sysMenu = BeanUtil.copyProperties(sysMenuAddOrUpdateDTO, SysMenu.class);
        sysMenu.setUpdateTime(DateTime.now());
        // TODO 设置更新者
        return ResponseResult.success(sysMenuService.updateById(sysMenu));
    }

    /**
     * 检查名字是否已经存在
     *
     * @param menuName  菜单名
     */
    private void checkNameRepeat(String menuName) {
        // 判断是否已经存在该部门名称
        var wrapper = new QueryWrapper<SysMenu>();
        wrapper.eq("menu_name", menuName);
        long count = sysMenuService.count(wrapper);
        if (count > 0) {
            throw new BusinessException(ResponseStatus.NAME_REPEAT_ERROR);
        }
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
    public List<SysTreeMenuVO> getTreeMenuList() {
        return sysMenuService.selectMenuTreeList();
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

