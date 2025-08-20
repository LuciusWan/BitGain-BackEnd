package com.lucius.bitgain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 任务操作DTO
 */
@Data
@Schema(description = "任务操作DTO")
public class TaskActionDTO {
    
    @NotNull(message = "任务ID不能为空")
    @Schema(description = "任务ID", example = "1")
    private Long taskId;
    
    @NotNull(message = "操作类型不能为空")
    @Pattern(regexp = "^(commit|reject)$", message = "操作类型只能是commit或reject")
    @Schema(description = "操作类型：commit-启用任务，reject-删除任务", example = "commit")
    private String action;
}