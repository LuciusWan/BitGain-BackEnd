package com.lucius.bitgain.service;

import com.lucius.bitgain.dto.TaskActionDTO;
import com.lucius.bitgain.utils.Result;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

public interface BitGainDesignService {
    void bitGainDesign(SseEmitter emitter,Long userId);
    
    /**
     * AI任务推荐
     * @return 推荐任务详情列表
     */
    Result<List<Map<String, Object>>> recommendTasks();
    
    /**
     * 确认推荐任务
     * @param taskActions 用户对推荐任务的操作列表
     * @return 确认结果
     */
    Result<String> confirmRecommendedTasks(List<TaskActionDTO> taskActions);
}
