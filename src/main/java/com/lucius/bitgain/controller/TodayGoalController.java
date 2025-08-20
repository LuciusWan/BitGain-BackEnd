package com.lucius.bitgain.controller;

import com.lucius.bitgain.dto.TodayGoalDTO;
import com.lucius.bitgain.service.TodayGoalService;
import com.lucius.bitgain.utils.Result;
import com.lucius.bitgain.vo.TodayGoalVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 今日目标管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/today-goal")
@RequiredArgsConstructor
@Validated
@Tag(name = "今日目标管理", description = "今日目标的增删改查操作")
public class TodayGoalController {

    private final TodayGoalService todayGoalService;

    /**
     * 创建今日目标
     *
     * @param todayGoalDTO 今日目标信息
     * @return 创建的今日目标信息
     */
    @PostMapping
    @Operation(summary = "创建今日目标", description = "创建一个新的今日目标")
    public Result<TodayGoalVO> createTodayGoal(
            @Valid @RequestBody TodayGoalDTO todayGoalDTO) {
        
        log.info("创建今日目标: {}", todayGoalDTO.getGoal());
        
        try {
            TodayGoalVO todayGoalVO = todayGoalService.createTodayGoal(todayGoalDTO);
            return Result.success(todayGoalVO);
        } catch (Exception e) {
            log.error("创建今日目标失败", e);
            return Result.error("创建今日目标失败: " + e.getMessage());
        }
    }

    /**
     * 删除今日目标
     *
     * @param id 目标ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除今日目标", description = "根据ID删除今日目标")
    public Result<Void> deleteTodayGoal(@PathVariable Long id) {
        
        log.info("删除今日目标: {}", id);
        
        try {
            todayGoalService.deleteTodayGoal(id);
            return Result.success(null);
        } catch (Exception e) {
            log.error("删除今日目标失败", e);
            return Result.error("删除今日目标失败: " + e.getMessage());
        }
    }

    /**
     * 更新今日目标
     *
     * @param id           目标ID
     * @param todayGoalDTO 更新的目标信息
     * @return 更新后的目标信息
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新今日目标", description = "根据ID更新今日目标")
    public Result<TodayGoalVO> updateTodayGoal(
            @PathVariable Long id,
            @Valid @RequestBody TodayGoalDTO todayGoalDTO) {
        
        log.info("更新今日目标 {}: {}", id, todayGoalDTO.getGoal());
        
        try {
            TodayGoalVO todayGoalVO = todayGoalService.updateTodayGoal(id, todayGoalDTO);
            return Result.success(todayGoalVO);
        } catch (Exception e) {
            log.error("更新今日目标失败", e);
            return Result.error("更新今日目标失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID查询今日目标
     *
     * @param id 目标ID
     * @return 今日目标信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询今日目标", description = "根据ID查询今日目标详情")
    public Result<TodayGoalVO> getTodayGoalById(@PathVariable Long id) {
        
        log.info("查询今日目标: {}", id);
        
        try {
            TodayGoalVO todayGoalVO = todayGoalService.getTodayGoalById(id);
            if (todayGoalVO != null) {
                return Result.success(todayGoalVO);
            } else {
                return Result.error("今日目标不存在或无权限访问");
            }
        } catch (Exception e) {
            log.error("查询今日目标失败", e);
            return Result.error("查询今日目标失败: " + e.getMessage());
        }
    }

    /**
     * 查询当前用户的所有今日目标
     *
     * @return 目标列表
     */
    @GetMapping("/my/all")
    @Operation(summary = "查询我的今日目标列表", description = "查询当前用户的所有今日目标")
    public Result<List<TodayGoalVO>> getMyTodayGoals() {
        
        log.info("查询我的所有今日目标");
        
        try {
            List<TodayGoalVO> todayGoals = todayGoalService.getMyTodayGoals();
            return Result.success(todayGoals);
        } catch (Exception e) {
            log.error("查询我的今日目标列表失败", e);
            return Result.error("查询今日目标列表失败: " + e.getMessage());
        }
    }

    /**
     * 删除当前用户的所有今日目标
     *
     * @return 删除结果
     */
    @DeleteMapping("/all")
    @Operation(summary = "删除我的所有今日目标", description = "删除当前用户的所有今日目标")
    public Result<Void> deleteAllMyTodayGoals() {
        
        log.info("删除我的所有今日目标");
        
        try {
            todayGoalService.deleteAllMyTodayGoals();
            return Result.success(null);
        } catch (Exception e) {
            log.error("删除所有今日目标失败", e);
            return Result.error("删除所有今日目标失败: " + e.getMessage());
        }
    }


}