package com.lucius.bitgain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息更新DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户信息更新请求")
public class UserUpdateDTO {

    @Schema(description = "用户名", example = "newusername")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "手机号", example = "13800138001")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Schema(description = "邮箱", example = "user@example.com")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "职业", example = "软件工程师")
    private String profession;

    @Schema(description = "技能标签，逗号分隔", example = "Java,Spring Boot,MySQL")
    private String skills;

    @Schema(description = "提升目标", example = "学习微服务架构,提升系统设计能力")
    private String goals;

    @Schema(description = "邮件订阅开关（1:开启,0:关闭）", example = "1")
    private Integer emailSubscribe;
}