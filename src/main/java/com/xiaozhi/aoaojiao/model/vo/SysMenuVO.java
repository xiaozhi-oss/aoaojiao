package com.xiaozhi.aoaojiao.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author xiaozhi
 */
@Data
public class SysMenuVO {

    private Long menuId;

    private String menuName;

    private Long parentId;

    private String path;

    private String query;

    private Integer isOuterChain;

    private Integer menuType;

    private Integer hidden;

    private Integer status;

    private String perms;

    private String icon;

    private Date createTime;

    private String remark;

    private List<SysMenuVO> childrenList;
}
