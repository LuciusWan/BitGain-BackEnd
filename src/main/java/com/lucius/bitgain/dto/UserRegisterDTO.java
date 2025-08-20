package com.lucius.bitgain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 用户注册数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户注册请求参数")
public class UserRegisterDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", example = "testuser", required = true)
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456", required = true)
    private String password;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "手机号", example = "13800138000", required = true)
    private String phone;
}