package com.lucius.bitgain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * 任务操作请求DTO
 */
@Data
@Schema(description = "任务操作请求DTO")
public class TaskActionsRequestDTO {
    @Valid
    @Schema(description = "任务操作列表")
    private List<TaskActionDTO> taskActions;
}