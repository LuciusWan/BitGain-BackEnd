package com.lucius.bitgain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * 用户登录数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户登录请求参数")
public class UserLoginDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", example = "123456", required = true)
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456", required = true)
    private String password;
}