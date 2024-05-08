package com.xiaozhi.aoaojiao.core.security.filter;

import cn.hutool.core.util.ObjectUtil;
import com.xiaozhi.aoaojiao.core.constants.RedisConstants;
import com.xiaozhi.aoaojiao.core.utils.RedisUtil;
import com.xiaozhi.aoaojiao.model.vo.LoginUserVo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author xiaozhi
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, 
                                    HttpServletResponse resp, 
                                    FilterChain chain) throws ServletException, IOException {
        var token = req.getHeader(tokenHeader);
        var loginUserVo = (LoginUserVo) redisUtil.get(RedisConstants.getLoginTokenKey(token));
        if (ObjectUtil.isNotEmpty(loginUserVo)) {
            var authenticationToken = new UsernamePasswordAuthenticationToken(loginUserVo, null, getPermissions(loginUserVo.getPermissions()));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 获取 token
        chain.doFilter(req, resp);
    }

    private List<GrantedAuthority> getPermissions(Set<String> permissions) {
        var authorities = new ArrayList<GrantedAuthority>();
        if (ObjectUtil.isNotEmpty(permissions)) {
            permissions.forEach(p -> authorities.add(() -> p));
        }
        return authorities;
    }

}
