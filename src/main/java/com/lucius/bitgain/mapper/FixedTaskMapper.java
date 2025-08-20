package com.lucius.bitgain.mapper;

import com.lucius.bitgain.entity.FixedTask;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 固定任务数据访问层
 */
@Mapper
public interface FixedTaskMapper {

    /**
     * 新增固定任务
     * @param fixedTask 固定任务信息
     */
    @Insert("INSERT INTO fixed_task (user_id, title, start_time, end_time, description, status, create_time, update_time,deleted) " +
            "VALUES (#{userId}, #{title}, #{startTime}, #{endTime}, #{description}, #{status}, #{createTime}, #{updateTime},#{deleted})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(FixedTask fixedTask);

    /**
     * 根据ID删除固定任务（软删除）
     * @param id 任务ID
     * @param userId 用户ID
     * @param updateTime 更新时间
     */
    @Update("UPDATE fixed_task SET deleted = 1, update_time = #{updateTime} WHERE id = #{id} AND user_id = #{userId} AND deleted = 0")
    void deleteById(@Param("id") Long id, @Param("userId") Long userId, @Param("updateTime") LocalDateTime updateTime);

    /**
     * 更新固定任务
     * @param fixedTask 固定任务信息
     */
    @Update("UPDATE fixed_task SET title = #{title}, start_time = #{startTime}, end_time = #{endTime}, " +
            "description = #{description}, status = #{status}, update_time = #{updateTime} " +
            "WHERE id = #{id} AND user_id = #{userId} AND deleted = 0")
    void update(FixedTask fixedTask);

    /**
     * 根据ID查询固定任务
     * @param id 任务ID
     * @param userId 用户ID
     * @return 固定任务信息
     */
    @Select("SELECT * FROM fixed_task WHERE id = #{id} AND user_id = #{userId} AND deleted = 0")
    FixedTask selectById(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 根据用户ID查询所有固定任务
     * @param userId 用户ID
     * @return 固定任务列表
     */
    @Select("SELECT * FROM fixed_task WHERE user_id = #{userId} AND deleted = 0 ORDER BY start_time ASC")
    List<FixedTask> selectByUserId(Long userId);

    /**
     * 根据用户ID和时间范围查询固定任务
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 固定任务列表
     */
    @Select("SELECT * FROM fixed_task WHERE user_id = #{userId} AND deleted = 0 " +
            "AND ((start_time >= #{startTime} AND start_time < #{endTime}) " +
            "OR (end_time > #{startTime} AND end_time <= #{endTime}) " +
            "OR (start_time < #{startTime} AND end_time > #{endTime})) " +
            "ORDER BY start_time ASC")
    List<FixedTask> selectByUserIdAndTimeRange(@Param("userId") Long userId, 
                                               @Param("startTime") LocalDateTime startTime, 
                                               @Param("endTime") LocalDateTime endTime);

    /**
     * 检查时间冲突（新增时）
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 冲突的任务数量
     */
    @Select("SELECT COUNT(*) FROM fixed_task WHERE user_id = #{userId} AND deleted = 0 " +
            "AND ((start_time < #{endTime} AND end_time > #{startTime}))")
    int checkTimeConflictForCreate(@Param("userId") Long userId, 
                                   @Param("startTime") LocalDateTime startTime, 
                                   @Param("endTime") LocalDateTime endTime);

    /**
     * 检查时间冲突（更新时，排除自己）
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeId 排除的任务ID
     * @return 冲突的任务数量
     */
    @Select("SELECT COUNT(*) FROM fixed_task WHERE user_id = #{userId} AND deleted = 0 " +
            "AND ((start_time < #{endTime} AND end_time > #{startTime})) " +
            "AND id != #{excludeId}")
    int checkTimeConflictForUpdate(@Param("userId") Long userId, 
                                   @Param("startTime") LocalDateTime startTime, 
                                   @Param("endTime") LocalDateTime endTime, 
                                   @Param("excludeId") Long excludeId);

    /**
     * 根据ID查询固定任务（包含已删除的记录）
     * @param id 任务ID
     * @param userId 用户ID
     * @return 固定任务信息
     */
    @Select("SELECT * FROM fixed_task WHERE id = #{id} AND user_id = #{userId}")
    FixedTask selectByIdIncludeDeleted(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 更新任务的删除状态
     * @param fixedTask 固定任务信息
     */
    @Update("UPDATE fixed_task SET deleted = #{deleted}, update_time = #{updateTime} ,status=#{status} " +
            "WHERE id = #{id} AND user_id = #{userId}")
    void updateDeleted(FixedTask fixedTask);
}