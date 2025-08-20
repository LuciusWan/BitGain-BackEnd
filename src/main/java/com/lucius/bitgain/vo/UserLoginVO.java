package com.lucius.bitgain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录响应视图对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户登录响应数据")
public class UserLoginVO {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "1")
    private Long userId;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "testuser")
    private String username;

    /**
     * JWT令牌
     */
    @Schema(description = "JWT访问令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}