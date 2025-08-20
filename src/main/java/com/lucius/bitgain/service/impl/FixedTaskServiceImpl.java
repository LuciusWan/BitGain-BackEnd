package com.lucius.bitgain.service.impl;

import com.lucius.bitgain.context.BaseContext;
import com.lucius.bitgain.dto.FixedTaskCreateDTO;
import com.lucius.bitgain.dto.FixedTaskUpdateDTO;
import com.lucius.bitgain.entity.FixedTask;
import com.lucius.bitgain.mapper.FixedTaskMapper;
import com.lucius.bitgain.service.FixedTaskService;
import com.lucius.bitgain.utils.Result;

import com.lucius.bitgain.vo.FixedTaskVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 固定任务业务逻辑实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FixedTaskServiceImpl implements FixedTaskService {

    private final FixedTaskMapper fixedTaskMapper;

    @Override
    @Transactional
    public Result<FixedTaskVO> createFixedTask(FixedTaskCreateDTO fixedTaskCreateDTO) {
        Long userId = BaseContext.getCurrentId();
        
        // 检查时间冲突
        int conflictCount = fixedTaskMapper.checkTimeConflictForCreate(
                userId, 
                fixedTaskCreateDTO.getStartTime(), 
                fixedTaskCreateDTO.getEndTime()
        );
        
        if (conflictCount > 0) {
            return Result.error("该时间段已有其他固定任务，请选择其他时间");
        }
        
        // 创建固定任务
        FixedTask fixedTask = FixedTask.builder()
                .userId(userId)
                .title(fixedTaskCreateDTO.getTitle())
                .startTime(fixedTaskCreateDTO.getStartTime())
                .endTime(fixedTaskCreateDTO.getEndTime())
                .description(fixedTaskCreateDTO.getDescription())
                .status(fixedTaskCreateDTO.getStatus())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .deleted(0)
                .build();
        
        fixedTaskMapper.insert(fixedTask);
        
        FixedTaskVO fixedTaskVO = convertToVO(fixedTask);
        
        log.info("用户{}创建固定任务成功，任务ID：{}", userId, fixedTask.getId());
        return Result.success(fixedTaskVO);
    }

    @Override
    @Transactional
    public Result<Void> deleteFixedTask(Long id) {
        Long userId = BaseContext.getCurrentId();
        
        // 检查任务是否存在
        FixedTask fixedTask = fixedTaskMapper.selectById(id, userId);
        if (fixedTask == null) {
            return Result.error("固定任务不存在或无权限删除");
        }
        
        fixedTaskMapper.deleteById(id, userId, LocalDateTime.now());
        
        log.info("用户{}删除固定任务成功，任务ID：{}", userId, id);
        return Result.success();
    }

    @Override
    @Transactional
    public Result<FixedTaskVO> updateFixedTask(FixedTaskUpdateDTO fixedTaskUpdateDTO) {
        Long userId = BaseContext.getCurrentId();
        
        // 检查任务是否存在
        FixedTask existingTask = fixedTaskMapper.selectById(fixedTaskUpdateDTO.getId(), userId);
        if (existingTask == null) {
            return Result.error("固定任务不存在或无权限修改");
        }
        
        // 检查时间冲突（排除当前任务）
        int conflictCount = fixedTaskMapper.checkTimeConflictForUpdate(
                userId, 
                fixedTaskUpdateDTO.getStartTime(), 
                fixedTaskUpdateDTO.getEndTime(), 
                fixedTaskUpdateDTO.getId()
        );
        
        if (conflictCount > 0) {
            return Result.error("该时间段已有其他固定任务，请选择其他时间");
        }
        
        // 更新固定任务
        FixedTask fixedTask = FixedTask.builder()
                .id(fixedTaskUpdateDTO.getId())
                .userId(userId)
                .title(fixedTaskUpdateDTO.getTitle())
                .startTime(fixedTaskUpdateDTO.getStartTime())
                .endTime(fixedTaskUpdateDTO.getEndTime())
                .description(fixedTaskUpdateDTO.getDescription())
                .status(fixedTaskUpdateDTO.getStatus())
                .updateTime(LocalDateTime.now())
                .build();
        
        fixedTaskMapper.update(fixedTask);
        
        // 查询更新后的任务
        FixedTask updatedTask = fixedTaskMapper.selectById(fixedTaskUpdateDTO.getId(), userId);
        FixedTaskVO fixedTaskVO = convertToVO(updatedTask);
        
        log.info("用户{}更新固定任务成功，任务ID：{}", userId, fixedTaskUpdateDTO.getId());
        return Result.success(fixedTaskVO);
    }

    @Override
    public Result<FixedTaskVO> getFixedTaskById(Long id) {
        Long userId = BaseContext.getCurrentId();
        
        FixedTask fixedTask = fixedTaskMapper.selectById(id, userId);
        if (fixedTask == null) {
            return Result.error("固定任务不存在或无权限查看");
        }
        
        FixedTaskVO fixedTaskVO = convertToVO(fixedTask);
        return Result.success(fixedTaskVO);
    }

    @Override
    public Result<List<FixedTaskVO>> getMyFixedTasks() {
        Long userId = BaseContext.getCurrentId();
        
        List<FixedTask> fixedTasks = fixedTaskMapper.selectByUserId(userId);
        List<FixedTaskVO> fixedTaskVOs = fixedTasks.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        return Result.success(fixedTaskVOs);
    }

    @Override
    public Result<List<FixedTaskVO>> getFixedTasksByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        Long userId = BaseContext.getCurrentId();
        
        if (startTime.isAfter(endTime)) {
            return Result.error("开始时间不能晚于结束时间");
        }
        
        List<FixedTask> fixedTasks = fixedTaskMapper.selectByUserIdAndTimeRange(userId, startTime, endTime);
        List<FixedTaskVO> fixedTaskVOs = fixedTasks.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        return Result.success(fixedTaskVOs);
    }

    /**
     * 将FixedTask实体转换为FixedTaskVO
     * @param fixedTask 固定任务实体
     * @return 固定任务视图对象
     */
    private FixedTaskVO convertToVO(FixedTask fixedTask) {
        return FixedTaskVO.builder()
                .id(fixedTask.getId())
                .userId(fixedTask.getUserId())
                .title(fixedTask.getTitle())
                .startTime(fixedTask.getStartTime())
                .endTime(fixedTask.getEndTime())
                .description(fixedTask.getDescription())
                .status(fixedTask.getStatus())
                .createTime(fixedTask.getCreateTime())
                .updateTime(fixedTask.getUpdateTime())
                .build();
    }
}