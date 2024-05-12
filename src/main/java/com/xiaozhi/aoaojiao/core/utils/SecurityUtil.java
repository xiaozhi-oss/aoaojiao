package com.xiaozhi.aoaojiao.core.utils;

import com.xiaozhi.aoaojiao.model.vo.LoginUserVo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author xiaozhi
 */
public class SecurityUtil {

    /**
     * 获取登录用户 ID
     * @return  用户ID
     */
    public static Long getLoginUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var loginUserVo = (LoginUserVo) authentication.getPrincipal();
        return loginUserVo.getUserId();
    }
}
