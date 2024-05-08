package com.xiaozhi.aoaojiao.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xiaozhi
 */
@Data
public class SysTreeMenuVO {

    private Long menuId;

    private String menuName;

    private List<SysTreeMenuVO> childrenList;
}
