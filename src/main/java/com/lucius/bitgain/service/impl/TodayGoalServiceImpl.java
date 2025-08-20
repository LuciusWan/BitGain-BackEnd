package com.lucius.bitgain.service.impl;

import com.lucius.bitgain.context.BaseContext;
import com.lucius.bitgain.dto.TodayGoalDTO;
import com.lucius.bitgain.entity.TodayGoal;
import com.lucius.bitgain.mapper.TodayGoalMapper;
import com.lucius.bitgain.service.TodayGoalService;
import com.lucius.bitgain.vo.TodayGoalVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 今日目标业务逻辑实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TodayGoalServiceImpl implements TodayGoalService {

    private final TodayGoalMapper todayGoalMapper;

    /**
     * 创建今日目标
     *
     * @param todayGoalDTO 今日目标信息
     * @return 今日目标视图对象
     */
    @Override
    @Transactional
    public TodayGoalVO createTodayGoal(TodayGoalDTO todayGoalDTO) {
        Long userId = BaseContext.getCurrentId();
        log.info("创建今日目标，用户ID: {}, 目标内容: {}", userId, todayGoalDTO.getGoal());
        
        LocalDateTime now = LocalDateTime.now();
        TodayGoal todayGoal = TodayGoal.builder()
                .userId(userId)
                .goal(todayGoalDTO.getGoal())
                .createTime(now)
                .updateTime(now)
                .build();
        
        int result = todayGoalMapper.insert(todayGoal);
        if (result <= 0) {
            throw new RuntimeException("创建今日目标失败");
        }
        
        log.info("今日目标创建成功，目标ID: {}", todayGoal.getId());
        return convertToVO(todayGoal);
    }

    /**
     * 删除今日目标
     *
     * @param id 目标ID
     */
    @Override
    @Transactional
    public void deleteTodayGoal(Long id) {
        Long userId = BaseContext.getCurrentId();
        log.info("删除今日目标，用户ID: {}, 目标ID: {}", userId, id);
        
        // 验证目标是否属于当前用户
        TodayGoal todayGoal = todayGoalMapper.selectByIdAndUserId(id, userId);
        if (todayGoal == null) {
            log.warn("今日目标不存在或不属于当前用户，用户ID: {}, 目标ID: {}", userId, id);
            throw new RuntimeException("今日目标不存在或无权限访问");
        }
        
        int result = todayGoalMapper.deleteById(id);
        if (result <= 0) {
            throw new RuntimeException("删除今日目标失败");
        }
        
        log.info("今日目标删除成功，目标ID: {}", id);
    }

    /**
     * 更新今日目标
     *
     * @param id           目标ID
     * @param todayGoalDTO 今日目标信息
     * @return 今日目标视图对象
     */
    @Override
    @Transactional
    public TodayGoalVO updateTodayGoal(Long id, TodayGoalDTO todayGoalDTO) {
        Long userId = BaseContext.getCurrentId();
        log.info("更新今日目标，用户ID: {}, 目标ID: {}, 新内容: {}", userId, id, todayGoalDTO.getGoal());
        
        // 验证目标是否属于当前用户
        TodayGoal existingGoal = todayGoalMapper.selectByIdAndUserId(id, userId);
        if (existingGoal == null) {
            log.warn("今日目标不存在或不属于当前用户，用户ID: {}, 目标ID: {}", userId, id);
            throw new RuntimeException("今日目标不存在或无权限访问");
        }
        
        TodayGoal todayGoal = TodayGoal.builder()
                .id(id)
                .goal(todayGoalDTO.getGoal())
                .updateTime(LocalDateTime.now())
                .build();
        
        int result = todayGoalMapper.update(todayGoal);
        if (result <= 0) {
            throw new RuntimeException("更新今日目标失败");
        }
        
        // 重新查询更新后的数据
        TodayGoal updatedGoal = todayGoalMapper.selectById(id);
        log.info("今日目标更新成功，目标ID: {}", id);
        
        return convertToVO(updatedGoal);
    }

    /**
     * 根据ID查询今日目标
     *
     * @param id 目标ID
     * @return 今日目标视图对象
     */
    @Override
    public TodayGoalVO getTodayGoalById(Long id) {
        Long userId = BaseContext.getCurrentId();
        log.info("查询今日目标，用户ID: {}, 目标ID: {}", userId, id);
        
        TodayGoal todayGoal = todayGoalMapper.selectByIdAndUserId(id, userId);
        if (todayGoal == null) {
            log.warn("今日目标不存在或不属于当前用户，用户ID: {}, 目标ID: {}", userId, id);
            return null;
        }
        
        return convertToVO(todayGoal);
    }

    /**
     * 查询当前用户的所有今日目标
     *
     * @return 今日目标列表
     */
    @Override
    public List<TodayGoalVO> getMyTodayGoals() {
        Long userId = BaseContext.getCurrentId();
        log.info("查询用户所有今日目标，用户ID: {}", userId);
        
        List<TodayGoal> todayGoals = todayGoalMapper.selectByUserId(userId);
        
        return todayGoals.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 删除当前用户的所有今日目标
     */
    @Override
    @Transactional
    public void deleteAllMyTodayGoals() {
        Long userId = BaseContext.getCurrentId();
        log.info("删除用户所有今日目标，用户ID: {}", userId);
        
        int result = todayGoalMapper.deleteByUserId(userId);
        if (result < 0) {
            throw new RuntimeException("删除用户所有今日目标失败");
        }
        
        log.info("用户所有今日目标删除成功，删除数量: {}", result);
    }

    /**
     * 将实体对象转换为视图对象
     *
     * @param todayGoal 今日目标实体
     * @return 今日目标视图对象
     */
    private TodayGoalVO convertToVO(TodayGoal todayGoal) {
        return TodayGoalVO.builder()
                .id(todayGoal.getId())
                .userId(todayGoal.getUserId())
                .goal(todayGoal.getGoal())
                .createTime(todayGoal.getCreateTime())
                .updateTime(todayGoal.getUpdateTime())
                .build();
    }
}