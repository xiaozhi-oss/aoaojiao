package com.xiaozhi.aoaojiao.core.config;

import com.xiaozhi.aoaojiao.core.interceptor.RepeatLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xiaozhi
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private RepeatLoginInterceptor repeatLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加重复登录拦截器，拦截重复重复登录的用户
        registry.addInterceptor(repeatLoginInterceptor)
                .addPathPatterns("/api/admin/login");
    }
}
