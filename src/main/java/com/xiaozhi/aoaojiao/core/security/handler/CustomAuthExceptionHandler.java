package com.xiaozhi.aoaojiao.core.security.handler;

import cn.hutool.json.JSONUtil;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xiaozhi
 *
 * 自定义认证授权失败处理器
 */
@Component
public class CustomAuthExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response(request, response, ResponseStatus.USER_UN_AUTHENTICATED);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response(request, response, ResponseStatus.USER_UN_UNAUTHORIZED);
    }

    private void response(HttpServletRequest request,
                          HttpServletResponse response,
                          ResponseStatus responseStatus) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JSONUtil.toJsonStr(ResponseResult.fail(responseStatus)));
    }
}
