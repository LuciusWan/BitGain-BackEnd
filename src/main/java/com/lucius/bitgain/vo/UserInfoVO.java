package com.lucius.bitgain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息响应视图对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户信息响应数据")
public class UserInfoVO {

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "testuser")
    private String username;

    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138001")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "user@example.com")
    private String email;

    /**
     * 职业
     */
    @Schema(description = "职业", example = "软件工程师")
    private String profession;

    /**
     * 技能标签，逗号分隔
     */
    @Schema(description = "技能标签", example = "Java,Spring Boot,MySQL")
    private String skills;

    /**
     * 提升目标
     */
    @Schema(description = "提升目标", example = "学习微服务架构,提升系统设计能力")
    private String goals;

    /**
     * 邮件订阅开关（1:开启,0:关闭）
     */
    @Schema(description = "邮件订阅开关", example = "1")
    private Integer emailSubscribe;
}