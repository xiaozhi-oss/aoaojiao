package com.xiaozhi.aoaojiao.core.config;

import com.xiaozhi.aoaojiao.core.security.filter.TokenAuthenticationFilter;
import com.xiaozhi.aoaojiao.core.security.handler.CustomAuthExceptionHandler;
import com.xiaozhi.aoaojiao.core.security.properties.SSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author xiaozhi
 */
@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAuthExceptionHandler exceptionHandler;

    @Autowired
    private SSProperties ssProperties;

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    /**
     * 设置密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 静态文件放行
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            ssProperties.getStaticPaths().forEach(s -> {
                web.ignoring().requestMatchers(s);
            });
        };
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 关闭 csrf -> 不关闭会导致除 get 请求的所有类型请求被拦截
                // 基于token的可以关闭，因为不存在 session
                .csrf(AbstractHttpConfigurer::disable)
                // 基于 token 模式，不需要 session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> {
                    ssProperties.getPublicPaths().forEach(s -> requests.requestMatchers(s).permitAll());
                    requests.anyRequest().authenticated();
                })
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(exceptionHandler);
                    ex.accessDeniedHandler(exceptionHandler);
                })
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}
