package com.lucius.bitgain.controller;

import com.lucius.bitgain.context.BaseContext;
import com.lucius.bitgain.dto.TaskActionDTO;
import com.lucius.bitgain.service.BitGainDesignService;
import com.lucius.bitgain.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bitgain-design")
@Tag(name = "AI智能设计", description = "AI任务推荐和智能设计相关接口")
public class BitGainDesignController {
    @Autowired
    private BitGainDesignService bitGainDesignService;
    
    @RequestMapping(value = "/design", produces = "text/event-stream")
    @Operation(summary = "AI智能设计", description = "基于SSE的AI智能设计功能")
    public SseEmitter bitGainDesign() {
        SseEmitter emitter = new SseEmitter(60000000L);
        bitGainDesignService.bitGainDesign(emitter, BaseContext.getCurrentId());
        return emitter;
    }
    
    /**
     * AI任务推荐
     * @return 推荐任务详情列表
     */
    @PostMapping("/recommend-tasks")
    @Operation(summary = "AI任务推荐", description = "根据用户职业、技能、目标和今日日程，推荐适合的碎片时间提升任务")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "推荐成功"),
            @ApiResponse(responseCode = "401", description = "用户未登录"),
            @ApiResponse(responseCode = "500", description = "推荐失败")
    })
    public Result<List<Map<String, Object>>> recommendTasks() {
        return bitGainDesignService.recommendTasks();
    }
    
    /**
     * 确认推荐任务
     * @param taskActions 用户对推荐任务的操作列表
     * @return 确认结果
     */
    @PostMapping("/confirm-tasks")
    @Operation(summary = "确认推荐任务", description = "用户确认选择的推荐任务，commit为启用（deleted=0），reject为删除")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "确认成功"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "401", description = "用户未登录"),
            @ApiResponse(responseCode = "500", description = "确认失败")
    })
    public Result<String> confirmRecommendedTasks(@RequestBody List<TaskActionDTO> taskActions) {
        return bitGainDesignService.confirmRecommendedTasks(taskActions);
    }
}
