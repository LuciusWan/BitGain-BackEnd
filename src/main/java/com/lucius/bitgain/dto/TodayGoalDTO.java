package com.lucius.bitgain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 今日目标数据传输对象
 * 用于接收客户端请求参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodayGoalDTO {

    /**
     * 目标ID（更新时需要）
     */
    private Long id;

    /**
     * 目标内容
     */
    @NotBlank(message = "目标内容不能为空")
    @Size(max = 500, message = "目标内容不能超过500个字符")
    private String goal;
}