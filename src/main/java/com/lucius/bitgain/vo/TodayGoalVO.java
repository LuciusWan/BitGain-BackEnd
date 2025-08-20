package com.lucius.bitgain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 今日目标视图对象
 * 用于返回给客户端的数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodayGoalVO {

    /**
     * 目标ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 目标内容
     */
    private String goal;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}