package com.lucius.bitgain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF保护
                .csrf(AbstractHttpConfigurer::disable)
                // 配置请求授权
                .authorizeHttpRequests(authz -> authz
                        // 允许所有请求访问，由JWT拦截器处理认证
                        .anyRequest().permitAll()
                )
                // 禁用表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                // 禁用HTTP Basic认证
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
}