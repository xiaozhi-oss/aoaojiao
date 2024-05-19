package com.xiaozhi.aoaojiao.model.dto;

import com.xiaozhi.aoaojiao.core.utils.PageDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xiaozhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserListDTO extends PageDto {

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 状态
     */
    @Min(value = 0, message = "状态最小值为 0")
    @Max(value = 1, message = "状态最大值为 1")
    private Integer status;
}
