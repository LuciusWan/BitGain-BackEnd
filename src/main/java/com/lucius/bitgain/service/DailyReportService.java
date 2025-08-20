package com.lucius.bitgain.service;

import com.lucius.bitgain.entity.User;

/**
 * 日报服务接口
 */
public interface DailyReportService {

    /**
     * 为指定用户生成并发送日报
     *
     * @param user 用户信息
     */
    void generateAndSendDailyReport(User user);

    /**
     * 为所有订阅用户发送日报
     */
    void sendDailyReportsToAllSubscribers();
}
