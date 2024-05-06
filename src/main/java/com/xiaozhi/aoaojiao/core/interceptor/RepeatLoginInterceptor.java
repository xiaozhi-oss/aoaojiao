package com.xiaozhi.aoaojiao.core.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.xiaozhi.aoaojiao.core.constants.RedisConstants;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.core.utils.JwtTokenUtil;
import com.xiaozhi.aoaojiao.core.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author xiaozhi
 *
 * 重复登录拦截器拦截：判断是否已经登录
 */
@Component
public class RepeatLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = jwtTokenUtil.getToken(request);
        if (ObjectUtil.isNotEmpty(token) && ObjectUtil.isEmpty(redisUtil.get(RedisConstants.getLoginTokenKey(token)))) {
            throw new BusinessException(ResponseStatus.LOGIN_REPEAT_ERROR);
        }
        return true;
    }
}
