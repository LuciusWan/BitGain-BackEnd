package com.lucius.bitgain.service;

import com.lucius.bitgain.dto.FixedTaskCreateDTO;
import com.lucius.bitgain.dto.FixedTaskUpdateDTO;
import com.lucius.bitgain.utils.Result;
import com.lucius.bitgain.vo.FixedTaskVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 固定任务业务逻辑接口
 */
public interface FixedTaskService {

    /**
     * 创建固定任务
     * @param fixedTaskCreateDTO 固定任务创建信息
     * @return 创建结果
     */
    Result<FixedTaskVO> createFixedTask(FixedTaskCreateDTO fixedTaskCreateDTO);

    /**
     * 删除固定任务
     * @param id 任务ID
     * @return 删除结果
     */
    Result<Void> deleteFixedTask(Long id);

    /**
     * 更新固定任务
     * @param fixedTaskUpdateDTO 固定任务更新信息
     * @return 更新结果
     */
    Result<FixedTaskVO> updateFixedTask(FixedTaskUpdateDTO fixedTaskUpdateDTO);

    /**
     * 根据ID查询固定任务
     * @param id 任务ID
     * @return 固定任务信息
     */
    Result<FixedTaskVO> getFixedTaskById(Long id);

    /**
     * 查询当前用户的所有固定任务
     * @return 固定任务列表
     */
    Result<List<FixedTaskVO>> getMyFixedTasks();

    /**
     * 根据时间范围查询当前用户的固定任务
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 固定任务列表
     */
    Result<List<FixedTaskVO>> getFixedTasksByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
}