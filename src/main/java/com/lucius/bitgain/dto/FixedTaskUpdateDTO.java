package com.lucius.bitgain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 固定任务更新DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "固定任务更新请求")
public class FixedTaskUpdateDTO {

    @Schema(description = "任务ID", example = "1")
    @NotNull(message = "任务ID不能为空")
    private Long id;

    @Schema(description = "任务标题", example = "晨间锻炼")
    @NotBlank(message = "任务标题不能为空")
    private String title;

    @Schema(description = "开始时间", example = "2024-01-15T07:00:00.000Z")
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime startTime;

    @Schema(description = "结束时间", example = "2024-01-15T08:00:00.000Z")
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime endTime;

    @Schema(description = "任务描述", example = "每日晨跑，保持身体健康")
    private String description;

    @Schema(description = "状态：pending-待开始，completed-已完成，abandoned-已放弃", example = "pending")
    @NotBlank(message = "状态不能为空")
    private String status;
}