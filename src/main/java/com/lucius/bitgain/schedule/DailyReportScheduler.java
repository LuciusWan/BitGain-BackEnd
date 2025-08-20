package com.lucius.bitgain.schedule;

import com.lucius.bitgain.service.DailyReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 日报定时任务调度器
 */
@Component
@Slf4j
public class DailyReportScheduler {

    @Autowired
    private DailyReportService dailyReportService;

    /**
     * 每日20:00发送日报
     * cron表达式: 秒 分 时 日 月 周
     * 0 0 20 * * ? 表示每天20:00:00执行
     */
    @Scheduled(cron = "0 33 20 * * ?")
    public void sendDailyReports() {
        log.info("开始执行日报发送定时任务");
        try {
            dailyReportService.sendDailyReportsToAllSubscribers();
            log.info("日报发送定时任务执行完成");
        } catch (Exception e) {
            log.error("日报发送定时任务执行失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 测试用的定时任务，每分钟执行一次（仅用于开发测试）
     * 生产环境请注释掉此方法
     */
    // @Scheduled(cron = "0 * * * * ?")
    public void sendDailyReportsForTest() {
        log.info("执行测试日报发送任务");
        try {
            dailyReportService.sendDailyReportsToAllSubscribers();
            log.info("测试日报发送任务执行完成");
        } catch (Exception e) {
            log.error("测试日报发送任务执行失败: {}", e.getMessage(), e);
        }
    }
}