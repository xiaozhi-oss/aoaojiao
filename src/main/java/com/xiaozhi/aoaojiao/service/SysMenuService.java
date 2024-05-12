package com.xiaozhi.aoaojiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.model.dto.SysMenuAddOrUpdateDTO;
import com.xiaozhi.aoaojiao.model.dto.SysMenuListDTO;
import com.xiaozhi.aoaojiao.model.entity.SysMenu;
import com.xiaozhi.aoaojiao.model.vo.SysMenuVO;
import com.xiaozhi.aoaojiao.model.vo.SysTreeMenuVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaozhi
 * @since 2024-04-09 03:56:45
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 添加或者更新菜单
     * @param sysMenuAddOrUpdateDTO 菜单DTO
     */
    void addOrUpdateMenu(SysMenuAddOrUpdateDTO sysMenuAddOrUpdateDTO);

    /**
     * 检查名字是否已经存在
     *
     * @param menuName  菜单名
     */
    void checkMenuNameRepeat(String menuName);

    /**
     * 获取仅有树形结构的数据
     * @return 树状列表
     */
    List<SysTreeMenuVO> selectMenuTreeList();

    /**
     * 获取所有菜单数据
     * @param sysMenuListDTO   接收参数对象
     * @return 所有部门数据
     */
    List<SysMenuVO> selectMenuList(SysMenuListDTO sysMenuListDTO);

    /**
     * 根据ID批量删除
     */
    void deleteMenuById(Long id) throws BusinessException;
}
