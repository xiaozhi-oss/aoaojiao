package com.xiaozhi.aoaojiao.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xiaozhi
 */
@Data
public class SysTreeDeptVO {

    private Long deptId;
    
    private String deptName;
    
    private List<SysTreeDeptVO> childrenList;
}
