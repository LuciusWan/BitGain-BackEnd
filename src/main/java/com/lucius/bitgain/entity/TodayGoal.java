package com.lucius.bitgain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 今日目标实体类
 * 对应数据库表：today_goal
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodayGoal {

    /**
     * 目标ID，主键
     */
    private Long id;

    /**
     * 用户ID，外键
     */
    private Long userId;

    /**
     * 目标内容
     */
    private String goal;

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
}