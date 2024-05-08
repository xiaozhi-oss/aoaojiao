package com.xiaozhi.aoaojiao.model.vo;

import com.xiaozhi.aoaojiao.model.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * @author xiaozhi
 *
 * 登录用户 VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long deptId;

    private String token;

    private SysUser sysUser;

    /**
     * 权限列表
     */
    private Set<String> permissions;
}
