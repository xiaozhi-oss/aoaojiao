package com.xiaozhi.aoaojiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.model.dto.dept.SysDeptListDTO;
import com.xiaozhi.aoaojiao.model.vo.dept.SysDeptVO;
import com.xiaozhi.aoaojiao.model.vo.dept.SysTreeDeptVO;
import com.xiaozhi.aoaojiao.model.entity.SysDept;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 03:56:45
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 获取仅有树形结构的数据
     * @return 树状列表
     */
    List<SysTreeDeptVO> selectDeptTreeList();

    /**
     * 获取所有部门数据
     * @param sysDeptListDTO   接收参数对象
     * @return 所有部门数据
     */
    List<SysDeptVO> selectDeptList(SysDeptListDTO sysDeptListDTO);

    /**
     * 根据ID批量删除
     * @return 返回结果
     */
    void deleteDeptById(Long id) throws BusinessException;
}
