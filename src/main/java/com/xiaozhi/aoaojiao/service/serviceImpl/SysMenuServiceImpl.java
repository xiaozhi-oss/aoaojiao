package com.xiaozhi.aoaojiao.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.core.utils.SecurityUtil;
import com.xiaozhi.aoaojiao.mapper.SysMenuMapper;
import com.xiaozhi.aoaojiao.model.dto.SysMenuAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysMenuListDTO;
import com.xiaozhi.aoaojiao.model.entity.SysMenu;
import com.xiaozhi.aoaojiao.model.vo.SysMenuVO;
import com.xiaozhi.aoaojiao.model.vo.SysTreeMenuVO;
import com.xiaozhi.aoaojiao.service.SysMenuService;
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
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public void addOrUpdateMenu(SysMenuAddOrUpdateDTO sysMenuAddOrUpdateDTO) {
        var userId = SecurityUtil.getLoginUserId();
        DateTime currentDate = DateTime.now();
        var sysMenu = BeanUtil.copyProperties(sysMenuAddOrUpdateDTO, SysMenu.class);
        // ID 为空则为添加
        int result = 0;
        if (ObjectUtil.isEmpty(sysMenu.getMenuId())) {
            // 添加需要检测是否已经存在该名称
            checkMenuNameRepeat(sysMenuAddOrUpdateDTO.getMenuName());
            sysMenu.setCreateBy(userId);
            sysMenu.setCreateTime(currentDate);
            result = sysMenuMapper.insert(sysMenu);
        } else {
            // ID 不为空为更新
            sysMenu.setUpdateBy(userId);
            sysMenu.setUpdateTime(currentDate);
            result = sysMenuMapper.updateById(sysMenu);
        }
        Assert.isTrue(result > 0,
                () -> BusinessException.build(ResponseStatus.OPERATION_ERROR));
    }

    @Override
    public void checkMenuNameRepeat(String menuName) {
        // 判断是否已经存在该部门名称
        var wrapper = new QueryWrapper<SysMenu>();
        wrapper.eq("menu_name", menuName);
        long count = sysMenuMapper.selectCount(wrapper);
        Assert.isTrue(count <= 0,
                () ->  BusinessException.build(ResponseStatus.NAME_REPEAT_ERROR));
    }

    @Override
    public List<SysTreeMenuVO> selectMenuTreeList() {
        var sysMenuVOList = this.selectMenuList(new SysMenuListDTO());
        return BeanUtil.copyToList(sysMenuVOList, SysTreeMenuVO.class);
    }

    @Override
    public List<SysMenuVO> selectMenuList(SysMenuListDTO sysMenuListDTO) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(sysMenuListDTO.getMenuName())) {
            wrapper.eq(SysMenu::getMenuName, sysMenuListDTO.getMenuName());
        }
        if (ObjectUtil.isNotEmpty(sysMenuListDTO.getStatus())) {
            wrapper.eq(SysMenu::getStatus, sysMenuListDTO.getStatus());
        }
        var deptList = sysMenuMapper.selectList(wrapper);
        // 获取树形结构数据
        List<SysMenuVO> sysDeptVOList = deptList.stream().map(dept ->
                BeanUtil.copyProperties(dept, SysMenuVO.class)).toList();
        return getDeptTree(sysDeptVOList);
    }

    /**
     * 构建树结构数据
     * @param deptList  部门list
     */
    private List<SysMenuVO> getDeptTree(List<SysMenuVO> deptList) {
        // 核心：子找父
        var deptTreeList = new ArrayList<SysMenuVO>();
        // 根据父ID进行分组
        List<Long> idList = deptList.stream().map(SysMenuVO::getMenuId).toList();
        Map<Long, List<SysMenuVO>> childrenMap = deptList.stream()
                .collect(Collectors.groupingBy(SysMenuVO::getParentId, Collectors.toList()));
        for (SysMenuVO sysMenuVO : deptList) {
            // 如果父节点不在id列表中，那么它就是顶级父节点
            Long parentId = sysMenuVO.getParentId();
            if (!idList.contains(parentId)) {
                getChildrenList(childrenMap, sysMenuVO);
                deptTreeList.add(sysMenuVO);
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
     * @param sysMenuVO   响应数据对象
     */
    private void getChildrenList(Map<Long, List<SysMenuVO>> map,
                                 SysMenuVO sysMenuVO) {
        // 获取子节点列表
        List<SysMenuVO> sysMenuVOList = map.get(sysMenuVO.getMenuId());
        if (ObjectUtils.isEmpty(sysMenuVOList)) {
            sysMenuVOList = ListUtil.empty();
        } else {
            // 遍历子节点，子节点获取它的子节点
            for (SysMenuVO sysMenu : sysMenuVOList) {
                Long deptId = sysMenu.getMenuId();
                if (map.containsKey(deptId)) {
                    getChildrenList(map, sysMenu);
                }
            }
        }
        sysMenuVO.setChildrenList(sysMenuVOList);
    }

    @Override
    public void deleteMenuById(Long id) throws BusinessException {
        checkMenuHasParentMenu(id);
        int count = sysMenuMapper.deleteById(id);
        Assert.isTrue(count > 0,
                () -> BusinessException.build(ResponseStatus.OPERATION_ERROR));
    }

    /**
     * 检查是否存在子节点，如果有，不允许删除
     * @param id    父ID
     */
    private void checkMenuHasParentMenu(Long id) {
        var wrapper = new LambdaQueryWrapper<SysMenu>();
        wrapper.eq(SysMenu::getParentId, id);
        Long count = sysMenuMapper.selectCount(wrapper);
        Assert.isTrue(count <= 0,
                () -> BusinessException.build(ResponseStatus.DELETE_OP_ERROR));
    }
}
