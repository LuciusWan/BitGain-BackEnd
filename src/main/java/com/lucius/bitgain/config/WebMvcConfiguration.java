package com.lucius.bitgain.config;


import com.lucius.bitgain.interceptor.JwtTokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置类
 * 统一处理跨域配置和拦截器配置
 */
@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private JwtTokenInterceptor jwtTokenInterceptor;

    /**
     * 配置跨域访问
     * 注意：跨域配置需要在拦截器之前处理
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("配置跨域访问...");
        registry.addMapping("/**")
                .allowedOriginPatterns("*")  // 使用 allowedOriginPatterns 替代 allowedOrigins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                .allowedHeaders("*")
                .allowCredentials(true)      // 允许携带凭证
                .maxAge(3600);              // 预检请求缓存时间
    }

    /**
     * 注册自定义拦截器
     * 注意：拦截器在跨域配置之后执行
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("注册JWT拦截器...");
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/register",      // 排除用户注册接口
                        "/user/login",         // 排除用户登录接口
                        "/login/**",           // 排除登录相关路径
                        "/ai/upload",          // 排除AI文档上传接口
                        "/test/cors",          // 排除跨域测试接口
                        "/error",              // 排除错误页面
                        "/favicon.ico",        // 排除图标请求
                        "/actuator/**",        // 排除监控端点
                        "/swagger-ui/**",      // 排除Swagger UI界面
                        "/v3/api-docs/**",     // 排除Swagger API文档
                        "/swagger-resources/**", // 排除Swagger资源
                        "/webjars/**"          // 排除webjars静态资源
                );
    }
}