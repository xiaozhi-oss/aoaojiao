package com.xiaozhi.aoaojiao.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import com.xiaozhi.aoaojiao.model.dto.SysDeptAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysDeptListDTO;
import com.xiaozhi.aoaojiao.model.entity.SysDept;
import com.xiaozhi.aoaojiao.model.vo.SysDeptVO;
import com.xiaozhi.aoaojiao.model.vo.SysTreeDeptVO;
import com.xiaozhi.aoaojiao.service.SysDeptService;
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
 * 部门相关接口
 *
 * @author xiaozhi
 * @since 2024-04-09 03:56:45
 */
@Tag(name = "部门相关接口")
@RestController
@RequestMapping("/api/dept")
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;

    @Operation(summary = "添加部门")
    @PostMapping("/save")
    public ResponseResult<Boolean> saveDept(@RequestBody @Valid SysDeptAddOrUpdateDTO sysDeptAddOrUpdateDTO) {
        checkNameRepeat(sysDeptAddOrUpdateDTO.getDeptName());
        SysDept sysDept = BeanUtil.copyProperties(sysDeptAddOrUpdateDTO, SysDept.class);
        sysDept.setCreateTime(DateTime.now());
        // TODO 设置创建者
        sysDept.setCreateBy(0L);
        return ResponseResult.success(sysDeptService.save(sysDept));
    }

    @Operation(summary = "更新部门")
    @PutMapping("/update")
    public ResponseResult<Boolean> updateDept(@RequestBody @Valid SysDeptAddOrUpdateDTO sysDeptAddOrUpdateDTO) {
        checkNameRepeat(sysDeptAddOrUpdateDTO.getDeptName());
        SysDept sysDept = BeanUtil.copyProperties(sysDeptAddOrUpdateDTO, SysDept.class);
        sysDept.setUpdateTime(DateTime.now());
        // TODO 设置更新者
        return ResponseResult.success(sysDeptService.updateById(sysDept));
    }

    /**
     * 检查名字是否已经存在
     *
     * @param deptName
     */
    private void checkNameRepeat(String deptName) {
        // 判断是否已经存在该部门名称
        var wrapper = new QueryWrapper<SysDept>();
        wrapper.eq("dept_name", deptName);
        long count = sysDeptService.count(wrapper);
        if (count > 0) {
            throw new BusinessException(ResponseStatus.NAME_REPEAT_ERROR);
        }
    }

    @Parameters({
            @Parameter(name = "deptName", description = "部门名称", in = ParameterIn.QUERY),
            @Parameter(name = "status", description = "部门状态", in = ParameterIn.QUERY),
    })
    @Operation(summary = "获取部门列表")
    @GetMapping("/list")
    public ResponseResult<List<SysDeptVO>> getDeptList(@Valid SysDeptListDTO sysDeptListDTO) {
        return ResponseResult.success(sysDeptService.selectDeptList(sysDeptListDTO));
    }

    @Operation(summary = "获取部门树状列表")
    @GetMapping("/treeList")
    public ResponseResult<List<SysTreeDeptVO>> getTreeDeptList() {
        return ResponseResult.success(sysDeptService.selectDeptTreeList());
    }

    @Parameters(
            @Parameter(name = "deptId", description = "部门ID", in = ParameterIn.QUERY)
    )
    @Operation(summary = "根据ID删除部门")
    @DeleteMapping("/deleteById")
    public ResponseResult<Void> deleteDeptById(@RequestBody Long deptId) {
        sysDeptService.deleteDeptById(deptId);
        return ResponseResult.success();
    }
}

