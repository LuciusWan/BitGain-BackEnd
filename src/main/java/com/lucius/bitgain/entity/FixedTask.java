package com.lucius.bitgain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 固定任务实体类
 * 对应数据库表：fixed_task
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixedTask {

    /**
     * 任务ID，主键
     */
    private Long id;

    /**
     * 用户ID，外键
     */
    private Long userId;

    /**
     * 任务标题
     */
    private String title;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime endTime;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 状态：pending-待开始，completed-已完成，abandoned-已放弃
     */
    private String status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime updateTime;

    /**
     * 软删除标记：0-未删除，1-已删除
     */
    private Integer deleted;
}