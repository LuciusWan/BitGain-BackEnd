package com.lucius.bitgain.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置类
 * 用于配置API文档的基本信息和JWT认证
 */
@Configuration
public class SwaggerConfig {

    /**
     * 配置OpenAPI信息
     * @return OpenAPI配置对象
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("碎时拾光 API 文档")
                        .description("碎片时间管理系统的REST API接口文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Lucius")
                                .email("lucius@example.com")))
                // 添加JWT认证配置
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("请输入JWT令牌，格式：Bearer {token}")));
    }
}