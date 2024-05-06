package com.xiaozhi.aoaojiao.service.strategy;

import com.xiaozhi.aoaojiao.core.enums.LoginType;
import com.xiaozhi.aoaojiao.model.dto.login.SysUserLoginDTO;
import com.xiaozhi.aoaojiao.model.vo.login.SysUserLoginVO;

/**
 * @author xiaozhi
 *
 * 登录策略抽象
 */
public interface LoginStrategy {

    /**
     * 登录接口
     * @return 登录结果
     */
    SysUserLoginVO login(SysUserLoginDTO sysUserLoginDTO);

    /**
     * 是否支持对应登录类型
     * @return
     */
    boolean supports(LoginType loginType);
}
