package com.xiaozhi.aoaojiao.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.mapper.SysDeptMapper;
import com.xiaozhi.aoaojiao.model.dto.dept.SysDeptListDTO;
import com.xiaozhi.aoaojiao.model.vo.dept.SysDeptVO;
import com.xiaozhi.aoaojiao.model.vo.dept.SysTreeDeptVO;
import com.xiaozhi.aoaojiao.model.entity.SysDept;
import com.xiaozhi.aoaojiao.service.SysDeptService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 03:56:45
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysTreeDeptVO> selectDeptTreeList() {
        List<SysDeptVO> deptList = selectDeptList(new SysDeptListDTO());
        return BeanUtil.copyToList(deptList, SysTreeDeptVO.class);
    }

    @Override
    public List<SysDeptVO> selectDeptList(SysDeptListDTO sysDeptListDTO) {
        QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(sysDeptListDTO.getDeptName())) {
            wrapper.like("dept_name", sysDeptListDTO.getDeptName());
        }
        if (!ObjectUtils.isEmpty(sysDeptListDTO.getStatus())) {
            wrapper.eq("status", sysDeptListDTO.getStatus());
        }
        var deptList = sysDeptMapper.selectList(wrapper);
        // 获取树形结构数据
        List<SysDeptVO> sysDeptVOList = deptList.stream().map(dept ->
                BeanUtil.copyProperties(dept, SysDeptVO.class)).toList();
        return getDeptTree(sysDeptVOList);
    }


    /**
     * 构建树结构数据
     * @param deptList  部门list
     */
    private List<SysDeptVO> getDeptTree(List<SysDeptVO> deptList) {
        // 核心：子找父
        var deptTreeList = new ArrayList<SysDeptVO>();
        // 根据父ID进行分组
        List<Long> idList = deptList.stream().map(SysDeptVO::getDeptId).toList();
        Map<Long, List<SysDeptVO>> childrenMap = deptList.stream()
                .collect(Collectors.groupingBy(SysDeptVO::getParentId, Collectors.toList()));
        for (SysDeptVO sysDeptVO : deptList) {
            // 如果父节点不在id列表中，那么它就是顶级父节点
            Long parentId = sysDeptVO.getParentId();
            if (!idList.contains(parentId)) {
                getChildrenList(childrenMap, sysDeptVO);
                deptTreeList.add(sysDeptVO);
            }
        }
        // 如果为空说明没有父节点，直接返回匹配的数据
        if (deptTreeList.isEmpty()) {
            return deptList;
        }
        // 退出条件就是遍历完成
        return deptTreeList;
    }

    /**
     * 获取子节点列表
     * @param map   父ID对应的子列表
     * @param sysDeptVO   响应数据对象
     */
    private void getChildrenList(Map<Long, List<SysDeptVO>> map, SysDeptVO sysDeptVO) {
        // 获取子节点列表
        List<SysDeptVO> sysDeptList = map.get(sysDeptVO.getDeptId());
        if (ObjectUtils.isEmpty(sysDeptList)) {
            sysDeptList = ListUtil.empty();
        }
        // 遍历子节点，子节点获取它的子节点
        for (SysDeptVO sysDept : sysDeptList) {
            Long deptId = sysDept.getDeptId();
            if (map.containsKey(deptId)) {
                getChildrenList(map, sysDept);
            }
        }
        sysDeptVO.setChildrenList(sysDeptList);
    }

    @Override
    public void deleteDeptById(Long id) {
        // 检查是否可删除
        checkDeleteDept(id);
        int isOk = sysDeptMapper.deleteById(id);
        if (isOk <= 0) {
            throw new BusinessException(ResponseStatus.OPERATION_ERROR);
        }
    }
    private void checkDeleteDept(Long id)  {
        var wrapper = new QueryWrapper<SysDept>();
        wrapper.eq("parent_id", id);
        var count = sysDeptMapper.selectCount(wrapper);
        if (count <= 0) {
            throw new BusinessException(ResponseStatus.DELETE_OP_ERROR);
        }
    }
}
