package com.lucius.bitgain.service;

import com.lucius.bitgain.dto.TodayGoalDTO;
import com.lucius.bitgain.vo.TodayGoalVO;

import java.util.List;

/**
 * 今日目标业务逻辑接口
 */
public interface TodayGoalService {

    /**
     * 创建今日目标
     *
     * @param todayGoalDTO 今日目标信息
     * @return 今日目标视图对象
     */
    TodayGoalVO createTodayGoal(TodayGoalDTO todayGoalDTO);

    /**
     * 删除今日目标
     *
     * @param id 目标ID
     */
    void deleteTodayGoal(Long id);

    /**
     * 更新今日目标
     *
     * @param id           目标ID
     * @param todayGoalDTO 今日目标信息
     * @return 今日目标视图对象
     */
    TodayGoalVO updateTodayGoal(Long id, TodayGoalDTO todayGoalDTO);

    /**
     * 根据ID查询今日目标
     *
     * @param id 目标ID
     * @return 今日目标视图对象
     */
    TodayGoalVO getTodayGoalById(Long id);

    /**
     * 查询当前用户的所有今日目标
     *
     * @return 今日目标列表
     */
    List<TodayGoalVO> getMyTodayGoals();

    /**
     * 删除当前用户的所有今日目标
     */
    void deleteAllMyTodayGoals();
}