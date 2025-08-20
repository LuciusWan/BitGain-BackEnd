package com.lucius.bitgain.controller;

import com.lucius.bitgain.dto.FixedTaskCreateDTO;
import com.lucius.bitgain.dto.FixedTaskUpdateDTO;
import com.lucius.bitgain.service.FixedTaskService;
import com.lucius.bitgain.utils.Result;
import com.lucius.bitgain.vo.FixedTaskVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 固定任务管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/fixed-task")
@RequiredArgsConstructor
@Tag(name = "固定任务管理", description = "固定任务的增删改查接口")
public class FixedTaskController {

    private final FixedTaskService fixedTaskService;

    /**
     * 创建固定任务
     * @param fixedTaskCreateDTO 固定任务创建信息
     * @return 创建结果
     */
    @PostMapping
    @Operation(summary = "创建固定任务", description = "创建新的固定任务，支持重复类型设置")
    public Result<FixedTaskVO> createFixedTask(@Valid @RequestBody FixedTaskCreateDTO fixedTaskCreateDTO) {
        return fixedTaskService.createFixedTask(fixedTaskCreateDTO);
    }

    /**
     * 删除固定任务
     * @param id 任务ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除固定任务", description = "根据任务ID删除固定任务（软删除）")
    public Result<Void> deleteFixedTask(
            @Parameter(description = "任务ID", required = true, example = "1")
            @PathVariable Long id) {
        return fixedTaskService.deleteFixedTask(id);
    }

    /**
     * 更新固定任务
     * @param fixedTaskUpdateDTO 固定任务更新信息
     * @return 更新结果
     */
    @PutMapping
    @Operation(summary = "更新固定任务", description = "更新固定任务信息")
    public Result<FixedTaskVO> updateFixedTask(@Valid @RequestBody FixedTaskUpdateDTO fixedTaskUpdateDTO) {
        return fixedTaskService.updateFixedTask(fixedTaskUpdateDTO);
    }

    /**
     * 根据ID查询固定任务
     * @param id 任务ID
     * @return 固定任务信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询固定任务详情", description = "根据任务ID查询固定任务详细信息")
    public Result<FixedTaskVO> getFixedTaskById(
            @Parameter(description = "任务ID", required = true, example = "1")
            @PathVariable Long id) {
        return fixedTaskService.getFixedTaskById(id);
    }

    /**
     * 查询当前用户的所有固定任务
     * @return 固定任务列表
     */
    @GetMapping("/my")
    @Operation(summary = "查询我的固定任务", description = "查询当前用户的所有固定任务列表")
    public Result<List<FixedTaskVO>> getMyFixedTasks() {
        return fixedTaskService.getMyFixedTasks();
    }

    /**
     * 根据时间范围查询固定任务
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 固定任务列表
     */
    @GetMapping("/range")
    @Operation(summary = "按时间范围查询固定任务", description = "根据指定时间范围查询当前用户的固定任务")
    public Result<List<FixedTaskVO>> getFixedTasksByTimeRange(
            @Parameter(description = "开始时间", required = true, example = "2024-01-15T00:00:00")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间", required = true, example = "2024-01-15T23:59:59")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endTime) {
        return fixedTaskService.getFixedTasksByTimeRange(startTime, endTime);
    }
}