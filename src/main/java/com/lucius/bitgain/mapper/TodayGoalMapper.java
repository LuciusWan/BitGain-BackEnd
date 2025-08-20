package com.lucius.bitgain.mapper;

import com.lucius.bitgain.entity.TodayGoal;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 今日目标数据访问层
 */
@Mapper
public interface TodayGoalMapper {

    /**
     * 创建今日目标
     *
     * @param todayGoal 今日目标信息
     * @return 影响行数
     */
    @Insert("INSERT INTO today_goal (user_id, goal, create_time, update_time) " +
            "VALUES (#{userId}, #{goal}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TodayGoal todayGoal);

    /**
     * 根据ID删除今日目标
     *
     * @param id 目标ID
     * @return 影响行数
     */
    @Delete("DELETE FROM today_goal WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 更新今日目标
     *
     * @param todayGoal 今日目标信息
     * @return 影响行数
     */
    @Update("UPDATE today_goal SET goal = #{goal}, update_time = #{updateTime} " +
            "WHERE id = #{id}")
    int update(TodayGoal todayGoal);

    /**
     * 根据ID查询今日目标
     *
     * @param id 目标ID
     * @return 今日目标信息
     */
    @Select("SELECT * FROM today_goal WHERE id = #{id}")
    TodayGoal selectById(Long id);

    /**
     * 根据用户ID查询今日目标列表
     *
     * @param userId 用户ID
     * @return 今日目标列表
     */
    @Select("SELECT * FROM today_goal WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<TodayGoal> selectByUserId(Long userId);

    /**
     * 根据用户ID和目标ID查询今日目标
     *
     * @param id     目标ID
     * @param userId 用户ID
     * @return 今日目标信息
     */
    @Select("SELECT * FROM today_goal WHERE id = #{id} AND user_id = #{userId}")
    TodayGoal selectByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 根据用户ID删除所有今日目标
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    @Delete("DELETE FROM today_goal WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);
}