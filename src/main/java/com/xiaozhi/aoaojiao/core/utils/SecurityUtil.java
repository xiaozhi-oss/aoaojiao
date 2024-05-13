package com.xiaozhi.aoaojiao.core.utils;

import com.xiaozhi.aoaojiao.model.vo.LoginUserVO;
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
        var loginUserVo = (LoginUserVO) authentication.getPrincipal();
        return loginUserVo.getUserId();
    }
}
