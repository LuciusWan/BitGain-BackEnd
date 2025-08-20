package com.lucius.bitgain.interceptor;

import com.lucius.bitgain.context.BaseContext;
import com.lucius.bitgain.properties.JwtProperties;
import com.lucius.bitgain.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验JWT令牌
     *
     * @param request  HTTP请求
     * @param response HTTP响应
     * @param handler  处理器
     * @return 是否放行
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 处理OPTIONS预检请求，直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            log.debug("OPTIONS预检请求，直接放行: {}", request.getRequestURI());
            return true;
        }

        // 判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            log.debug("非Controller方法，直接放行: {}", request.getRequestURI());
            return true;
        }

        // 从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());
        log.debug("请求路径: {}, Token: {}", request.getRequestURI(), token != null ? "存在" : "不存在");

        // 校验令牌
        try {
            if (token == null || token.trim().isEmpty()) {
                log.warn("JWT令牌为空，请求路径: {}", request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            
            // 处理Bearer前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7); // 去掉"Bearer "前缀
            }
            
            // 再次检查token是否为空
            if (token.trim().isEmpty()) {
                log.warn("JWT令牌去除Bearer前缀后为空，请求路径: {}", request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = Long.valueOf(claims.get("userId").toString());
            
            // 将用户ID存储到上下文中
            BaseContext.setCurrentId(userId);
            log.debug("JWT校验成功，用户ID: {}, 请求路径: {}", userId, request.getRequestURI());
            
            return true;
        } catch (Exception ex) {
            log.error("JWT校验失败，请求路径: {}, 错误信息: {}", request.getRequestURI(), ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    /**
     * 请求处理完成后清理上下文
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理ThreadLocal，避免内存泄漏
        BaseContext.removeCurrentId();
    }
}
