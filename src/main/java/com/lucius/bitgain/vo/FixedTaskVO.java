package com.lucius.bitgain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 固定任务视图对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "固定任务信息")
public class FixedTaskVO {

    @Schema(description = "任务ID", example = "1")
    private Long id;

    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @Schema(description = "任务标题", example = "晨间锻炼")
    private String title;

    @Schema(description = "开始时间", example = "2024-01-15T07:00:00.000Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime startTime;

    @Schema(description = "结束时间", example = "2024-01-15T08:00:00.000Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime endTime;

    @Schema(description = "任务描述", example = "每日晨跑，保持身体健康")
    private String description;

    @Schema(description = "状态：pending-待开始，completed-已完成，abandoned-已放弃", example = "pending")
    private String status;

    @Schema(description = "创建时间", example = "2024-01-15T06:00:00.000Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", example = "2024-01-15T06:00:00.000Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime updateTime;
}