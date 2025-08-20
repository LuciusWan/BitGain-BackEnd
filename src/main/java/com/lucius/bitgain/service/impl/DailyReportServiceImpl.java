package com.lucius.bitgain.service.impl;

import com.lucius.bitgain.entity.FixedTask;
import com.lucius.bitgain.entity.TodayGoal;
import com.lucius.bitgain.entity.User;
import com.lucius.bitgain.mapper.FixedTaskMapper;
import com.lucius.bitgain.mapper.TodayGoalMapper;
import com.lucius.bitgain.mapper.UserMapper;
import com.lucius.bitgain.service.DailyReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 日报服务实现类
 */
@Service
@Slf4j
public class DailyReportServiceImpl implements DailyReportService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TodayGoalMapper todayGoalMapper;

    @Autowired
    private FixedTaskMapper fixedTaskMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void generateAndSendDailyReport(User user) {
        try {
            log.info("开始为用户 {} 生成日报", user.getUsername());

            // 查询用户今日目标
            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
            List<TodayGoal> todayGoals = todayGoalMapper.selectByUserIdAndTime(user.getId(), startOfDay, endOfDay);
            
            // 查询用户今日固定任务
            List<FixedTask> todayTasks = fixedTaskMapper.selectByUserIdAndTimeRange(user.getId(), startOfDay, endOfDay);

            // 使用模板生成日报内容
            String reportContent = generateDailyReportTemplate(user, todayGoals, todayTasks);

            // 发送邮件
            sendEmailReport(user, reportContent);
            log.info("用户 {} 的日报发送成功", user.getUsername());

        } catch (Exception e) {
            log.error("为用户 {} 生成日报失败: {}", user.getUsername(), e.getMessage());
        }
    }

    @Override
    public void sendDailyReportsToAllSubscribers() {
        try {
            log.info("开始为所有订阅用户发送日报");
            
            // 查询所有开启邮件订阅的用户
            List<User> subscribedUsers = userMapper.getSubscribedUsers();
            log.info("找到 {} 个订阅用户", subscribedUsers.size());

            // 为每个用户异步生成并发送日报
            for (User user : subscribedUsers) {
                CompletableFuture.runAsync(() -> generateAndSendDailyReport(user));
            }

            log.info("日报发送任务已启动");
        } catch (Exception e) {
            log.error("批量发送日报失败: {}", e.getMessage());
        }
    }

    /**
     * 构建日报AI提示内容
     */
    /**
     * 生成日报模板内容
     */
    private String generateDailyReportTemplate(User user, List<TodayGoal> todayGoals, List<FixedTask> todayTasks) {
        StringBuilder content = new StringBuilder();
        LocalDate today = LocalDate.now();
        String todayStr = today.toString();
        
        content.append("<html><head><meta charset='UTF-8'></head><body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: 0 auto; padding: 20px;'>");
        
        // 头部标题
        content.append("<div style='background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; border-radius: 10px; text-align: center; margin-bottom: 20px;'>");
        content.append("<h1 style='margin: 0; font-size: 24px;'>📊 每日总结报告</h1>");
        content.append("<p style='margin: 10px 0 0 0; opacity: 0.9;'>").append(todayStr).append("</p>");
        content.append("</div>");
        
        // 问候语
        content.append("<div style='background: #f8f9fa; padding: 15px; border-radius: 8px; margin-bottom: 20px;'>");
        content.append("<p style='margin: 0;'>亲爱的 <strong>").append(user.getUsername()).append("</strong>，</p>");
        content.append("<p style='margin: 10px 0 0 0;'>感谢您使用碎时拾光进行时间管理！以下是您今日的目标回顾和总结。</p>");
        content.append("</div>");
        
        // 今日目标部分
        content.append("<div style='background: white; border: 1px solid #e9ecef; border-radius: 8px; padding: 20px; margin-bottom: 20px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);'>");
        content.append("<h2 style='color: #495057; margin-top: 0; border-bottom: 2px solid #007bff; padding-bottom: 10px;'>🎯 今日目标回顾</h2>");
        
        if (todayGoals.isEmpty()) {
            content.append("<div style='background: #fff3cd; border: 1px solid #ffeaa7; border-radius: 5px; padding: 15px;'>");
            content.append("<p style='margin: 0; color: #856404;'>📝 今日暂无设定目标</p>");
            content.append("<p style='margin: 10px 0 0 0; color: #856404; font-size: 14px;'>建议明天为自己制定一些小目标，让每一天都更有方向！</p>");
            content.append("</div>");
        } else {
            content.append("<div style='background: #d4edda; border: 1px solid #c3e6cb; border-radius: 5px; padding: 15px; margin-bottom: 15px;'>");
            content.append("<p style='margin: 0; color: #155724; font-weight: bold;'>✅ 目标完成情况</p>");
            content.append("<p style='margin: 5px 0 0 0; color: #155724;'>今日共设定 <strong>").append(todayGoals.size()).append("</strong> 个目标</p>");
            content.append("</div>");
            
            content.append("<ul style='list-style: none; padding: 0;'>");
            for (int i = 0; i < todayGoals.size(); i++) {
                TodayGoal goal = todayGoals.get(i);
                content.append("<li style='background: #f8f9fa; margin: 8px 0; padding: 12px; border-left: 4px solid #007bff; border-radius: 4px;'>");
                content.append("<span style='color: #007bff; font-weight: bold;'>目标 ").append(i + 1).append(":</span> ");
                content.append(goal.getGoal());
                content.append("</li>");
            }
            content.append("</ul>");
            
            content.append("<p style='color: #28a745; font-style: italic; margin-top: 15px;'>🌟 每一个小目标都是进步的开始，为您的坚持点赞！</p>");
        }
        content.append("</div>");
        
        // 任务完成情况分析部分
        content.append("<div style='background: white; border: 1px solid #e9ecef; border-radius: 8px; padding: 20px; margin-bottom: 20px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);'>");
        content.append("<h2 style='color: #495057; margin-top: 0; border-bottom: 2px solid #17a2b8; padding-bottom: 10px;'>📋 任务完成情况</h2>");
        
        if (todayTasks.isEmpty()) {
            content.append("<div style='background: #f8d7da; border: 1px solid #f5c6cb; border-radius: 5px; padding: 15px;'>");
            content.append("<p style='margin: 0; color: #721c24;'>📝 今日暂无安排任务</p>");
            content.append("<p style='margin: 10px 0 0 0; color: #721c24; font-size: 14px;'>建议明天为自己安排一些具体的任务，让时间更有价值！</p>");
            content.append("</div>");
        } else {
            // 统计任务状态
            long completedCount = todayTasks.stream().filter(task -> "completed".equals(task.getStatus())).count();
            long pendingCount = todayTasks.stream().filter(task -> "pending".equals(task.getStatus())).count();
            long abandonedCount = todayTasks.stream().filter(task -> "abandoned".equals(task.getStatus())).count();
            int totalTasks = todayTasks.size();
            double completionRate = totalTasks > 0 ? (double) completedCount / totalTasks * 100 : 0;
            
            // 完成率概览
            content.append("<div style='background: #d1ecf1; border: 1px solid #bee5eb; border-radius: 5px; padding: 15px; margin-bottom: 15px;'>");
            content.append("<p style='margin: 0; color: #0c5460; font-weight: bold;'>📊 完成率统计</p>");
            content.append("<p style='margin: 5px 0 0 0; color: #0c5460;'>今日共安排 <strong>").append(totalTasks).append("</strong> 个任务，完成率为 <strong>").append(String.format("%.1f", completionRate)).append("%</strong></p>");
            content.append("<div style='margin-top: 10px;'>");
            content.append("<span style='background: #28a745; color: white; padding: 3px 8px; border-radius: 3px; margin-right: 8px; font-size: 12px;'>✅ 已完成: ").append(completedCount).append("</span>");
            content.append("<span style='background: #ffc107; color: #212529; padding: 3px 8px; border-radius: 3px; margin-right: 8px; font-size: 12px;'>⏳ 待完成: ").append(pendingCount).append("</span>");
            content.append("<span style='background: #dc3545; color: white; padding: 3px 8px; border-radius: 3px; font-size: 12px;'>❌ 已放弃: ").append(abandonedCount).append("</span>");
            content.append("</div>");
            content.append("</div>");
            
            // 任务详情列表
            if (!todayTasks.isEmpty()) {
                content.append("<h3 style='color: #495057; margin: 20px 0 10px 0;'>📝 任务详情</h3>");
                content.append("<ul style='list-style: none; padding: 0;'>");
                for (FixedTask task : todayTasks) {
                    String statusColor = "completed".equals(task.getStatus()) ? "#28a745" : 
                                       "pending".equals(task.getStatus()) ? "#ffc107" : "#dc3545";
                    String statusIcon = "completed".equals(task.getStatus()) ? "✅" : 
                                      "pending".equals(task.getStatus()) ? "⏳" : "❌";
                    String statusText = "completed".equals(task.getStatus()) ? "已完成" : 
                                      "pending".equals(task.getStatus()) ? "待完成" : "已放弃";
                    
                    content.append("<li style='background: #f8f9fa; margin: 8px 0; padding: 12px; border-left: 4px solid ").append(statusColor).append("; border-radius: 4px;'>");
                    content.append("<div style='display: flex; justify-content: space-between; align-items: center;'>");
                    content.append("<div>");
                    content.append("<span style='font-weight: bold; color: #495057;'>").append(task.getTitle()).append("</span>");
                    if (task.getDescription() != null && !task.getDescription().trim().isEmpty()) {
                        content.append("<br><span style='color: #6c757d; font-size: 14px;'>").append(task.getDescription()).append("</span>");
                    }
                    content.append("<br><span style='color: #6c757d; font-size: 12px;'>时间: ").append(task.getStartTime().toLocalTime()).append(" - ").append(task.getEndTime().toLocalTime()).append("</span>");
                    content.append("</div>");
                    content.append("<span style='background: ").append(statusColor).append("; color: white; padding: 4px 8px; border-radius: 12px; font-size: 12px; white-space: nowrap;'>").append(statusIcon).append(" ").append(statusText).append("</span>");
                    content.append("</div>");
                    content.append("</li>");
                }
                content.append("</ul>");
            }
            
            // 完成情况评价
            if (completionRate >= 80) {
                content.append("<p style='color: #28a745; font-style: italic; margin-top: 15px;'>🎉 完成率很高！您的时间管理能力很棒，继续保持！</p>");
            } else if (completionRate >= 60) {
                content.append("<p style='color: #ffc107; font-style: italic; margin-top: 15px;'>👍 完成率不错！还有提升空间，加油！</p>");
            } else if (completionRate >= 40) {
                content.append("<p style='color: #fd7e14; font-style: italic; margin-top: 15px;'>💪 完成率有待提高，建议合理安排任务量和时间。</p>");
            } else {
                content.append("<p style='color: #dc3545; font-style: italic; margin-top: 15px;'>🤔 今日完成率较低，建议反思任务安排是否合理，明日调整计划。</p>");
            }
        }
        content.append("</div>");
        
        // 用户信息部分
        if (user.getProfession() != null || user.getGoals() != null) {
            content.append("<div style='background: white; border: 1px solid #e9ecef; border-radius: 8px; padding: 20px; margin-bottom: 20px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);'>");
            content.append("<h2 style='color: #495057; margin-top: 0; border-bottom: 2px solid #28a745; padding-bottom: 10px;'>👤 个人信息</h2>");
            
            if (user.getProfession() != null) {
                content.append("<p style='margin: 10px 0;'><strong>💼 职业：</strong>").append(user.getProfession()).append("</p>");
            }
            if (user.getGoals() != null) {
                content.append("<p style='margin: 10px 0;'><strong>🎯 提升目标：</strong>").append(user.getGoals()).append("</p>");
            }
            content.append("</div>");
        }
        
        // 明日建议部分
        content.append("<div style='background: white; border: 1px solid #e9ecef; border-radius: 8px; padding: 20px; margin-bottom: 20px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);'>");
        content.append("<h2 style='color: #495057; margin-top: 0; border-bottom: 2px solid #ffc107; padding-bottom: 10px;'>💡 明日建议</h2>");
        content.append("<div style='background: #fff3cd; border-radius: 5px; padding: 15px;'>");
        content.append("<ul style='margin: 0; padding-left: 20px; color: #856404;'>");
        content.append("<li style='margin: 8px 0;'>继续保持良好的目标设定习惯，让每一天都有明确方向</li>");
        content.append("<li style='margin: 8px 0;'>合理安排时间，劳逸结合，保持身心健康</li>");
        content.append("<li style='margin: 8px 0;'>记录每日收获，积累成长经验，见证自己的进步</li>");
        content.append("<li style='margin: 8px 0;'>善用碎片时间，让时间管理成为生活的好习惯</li>");
        content.append("</ul>");
        content.append("</div>");
        content.append("</div>");
        
        // 结尾祝福
        content.append("<div style='background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; border-radius: 10px; text-align: center;'>");
        content.append("<p style='margin: 0; font-size: 18px; font-weight: bold;'>🌟 祝您明天更加精彩！</p>");
        content.append("<p style='margin: 10px 0 0 0; opacity: 0.9; font-size: 14px;'>来自碎时拾光团队</p>");
        content.append("</div>");
        
        content.append("</body></html>");
        
        return content.toString();
    }

    /**
     * 发送邮件日报
     */
    private void sendEmailReport(User user, String content) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom(fromEmail);
        helper.setTo(user.getEmail());
        helper.setSubject("📊 您的每日总结 - " + LocalDate.now());
        helper.setText(content, true); // true表示HTML格式
        
        mailSender.send(message);
        log.info("日报邮件已发送至: {}", user.getEmail());
    }
}