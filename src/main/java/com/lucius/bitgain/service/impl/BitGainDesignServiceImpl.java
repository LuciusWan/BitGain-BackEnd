package com.lucius.bitgain.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lucius.bitgain.constant.AIConstant;
import com.lucius.bitgain.context.BaseContext;
import com.lucius.bitgain.dto.TaskActionDTO;
import com.lucius.bitgain.entity.FixedTask;
import com.lucius.bitgain.entity.TodayGoal;
import com.lucius.bitgain.entity.User;
import com.lucius.bitgain.mapper.FixedTaskMapper;
import com.lucius.bitgain.mapper.TodayGoalMapper;
import com.lucius.bitgain.mapper.UserMapper;
import com.lucius.bitgain.service.BitGainDesignService;
import com.lucius.bitgain.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class BitGainDesignServiceImpl implements BitGainDesignService {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Autowired
    private ChatClient bitGainChatClient;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private FixedTaskMapper fixedTaskMapper;
    @Autowired
    private TodayGoalMapper todayGoalMapper;
    @Override
    public void bitGainDesign(SseEmitter emitter, Long userId) {
        executorService.submit(() -> {
            try {
                // 发送开始信息
                
                // 获取当前用户ID
                if (userId == null) {
                    emitter.completeWithError(new RuntimeException("用户未登录"));
                    return;
                }
                
                // 查询用户信息
                User user = userMapper.getUserById(userId);
                if (user == null) {
                    return;
                }
                
                // 查询今日固定任务
                LocalDate today = LocalDate.now();
                LocalDateTime startOfDay = today.atStartOfDay();
                LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();
                List<FixedTask> todayTasks = fixedTaskMapper.selectByUserIdAndTimeRange(userId, startOfDay, endOfDay);
                List<TodayGoal> todayGoals = todayGoalMapper.selectByUserIdAndTime(userId,startOfDay, endOfDay);
                // 构建AI提示内容
                String userPrompt = buildUserPrompt(user, todayTasks, todayGoals);
                
                // 调用AI接口生成推荐
                StringBuilder aiResponse = new StringBuilder();
                
                bitGainChatClient.prompt(AIConstant.MAIN+" /no-think ")
                        .user(userPrompt+"```json ```是不合法的，不允许出现")
                        .stream()
                        .content()
                        .subscribe(
                                // 每次收到AI响应片段时发送给前端
                                aiResponse::append,
                                error -> {
                                    log.error("AI调用失败", error);
                                    try {
                                        emitter.send("错误：AI调用失败 - " + error.getMessage());
                                        emitter.completeWithError(error);
                                    } catch (IOException e) {
                                        log.error("发送错误消息失败", e);
                                    }
                                },
                                () -> {
                                    try {
                                        // AI响应完成，解析并保存任务
                                        System.out.println("AI响应: " + aiResponse);
                                        List<Map<String, Object>> taskDetails = parseAIResponseAndSaveTasks(aiResponse.toString(), userId);
                                        
                                        // 发送任务详情给前端
                                        Gson gson = new Gson();
                                        emitter.send(SseEmitter.event()
                                            .data(gson.toJson(taskDetails)));
                                        emitter.send("end");
                                        emitter.complete();
                                        
                                        log.info("用户{}的AI任务推荐完成，生成{}个任务", userId, taskDetails.size());
                                    } catch (Exception e) {
                                        log.error("解析AI响应失败", e);
                                        try {
                                            emitter.send("错误：解析AI响应失败 - " + e.getMessage());
                                            emitter.completeWithError(e);
                                        } catch (IOException ioException) {
                                            log.error("发送错误消息失败", ioException);
                                        }
                                    }
                                }
                        );
                        
            } catch (Exception e) {
                log.error("bitGainDesign执行失败", e);
                try {
                    emitter.send("系统错误：" + e.getMessage());
                    emitter.completeWithError(e);
                } catch (IOException ioException) {
                    log.error("发送错误消息失败", ioException);
                }
            }
        });
    }
    
    @Override
    public Result<List<Map<String, Object>>> recommendTasks() {
        try {
            // 获取当前用户ID
            Long userId = BaseContext.getCurrentId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            log.info("开始为用户{}生成AI任务推荐", userId);
            
            // 1. 查询用户信息
            User user = userMapper.getUserById(userId);
            if (user == null) {
                return Result.error("用户不存在");
            }
            
            // 2. 查询用户今日固定任务
            LocalDate today = LocalDate.now();
            LocalDateTime startOfDay = today.atStartOfDay();
            LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();
            List<FixedTask> todayTasks = fixedTaskMapper.selectByUserIdAndTimeRange(userId, startOfDay, endOfDay);
            
            // 查询用户今日目标
            List<TodayGoal> todayGoals = todayGoalMapper.selectByUserIdAndTime(userId, startOfDay, endOfDay);
            
            // 3. 构建AI提示内容
            String userPrompt = buildUserPrompt(user, todayTasks, todayGoals);
            log.info("构建的用户提示内容: {}", userPrompt);
            
            // 4. 调用AI接口
            String aiResponse = bitGainChatClient.prompt()
                    .system(AIConstant.MAIN)
                    .user(userPrompt)
                    .call()
                    .content();
            
            log.info("AI返回内容: {}", aiResponse);
            
            // 5. 解析AI返回的JSON
            List<Map<String, Object>> taskDetails = parseAIResponseAndSaveTasks(aiResponse, userId);

            log.info("成功生成{}个推荐任务", taskDetails.size());
            return Result.success(taskDetails);
            
        } catch (Exception e) {
            log.error("AI任务推荐失败", e);
            return Result.error("AI任务推荐失败: " + e.getMessage());
        }
    }
    
    /**
     * 构建发送给AI的用户提示内容
     */
    private String buildUserPrompt(User user, List<FixedTask> todayTasks, List<TodayGoal> todayGoals) {
        StringBuilder prompt = new StringBuilder();
        
        // 用户基本信息
        prompt.append("用户信息:\n");
        prompt.append("职业: ").append(user.getProfession() != null ? user.getProfession() : "未设置").append("\n");
        prompt.append("技能: ").append(user.getSkills() != null ? user.getSkills() : "未设置").append("\n");
        prompt.append("目标: ").append(user.getGoals() != null ? user.getGoals() : "未设置").append("\n");
        
        // 今日目标
        if (todayGoals != null && !todayGoals.isEmpty()) {
            prompt.append("今日目标:\n");
            for (TodayGoal goal : todayGoals) {
                 prompt.append("- ").append(goal.getGoal()).append("\n");
             }
        }
        prompt.append("\n");
        
        // 今日日程
        prompt.append("今日已安排的固定任务:\n");
        if (todayTasks.isEmpty()) {
            prompt.append("暂无固定任务安排\n");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            for (FixedTask task : todayTasks) {
                prompt.append("- ").append(task.getTitle())
                      .append(" (").append(task.getStartTime().format(formatter))
                      .append("-").append(task.getEndTime().format(formatter))
                      .append(")\n");
            }
        }
        
        prompt.append("\n请根据用户的职业、技能、目标、今日目标和今日日程，推荐3-5个适合的碎片时间提升任务。");
        
        return prompt.toString();
    }
    
    /**
     * 解析AI返回的JSON并保存推荐任务到数据库
     */
    private List<Map<String, Object>> parseAIResponseAndSaveTasks(String aiResponse, Long userId) {
        List<Map<String, Object>> taskDetails = new ArrayList<>();
        
        try {
            // 解析JSON
            JsonObject jsonObject = JsonParser.parseString(aiResponse).getAsJsonObject();
            JsonArray tasksArray = jsonObject.getAsJsonArray("tasks");
            
            for (JsonElement taskElement : tasksArray) {
                JsonObject taskObj = taskElement.getAsJsonObject();
                
                // 创建FixedTask对象
                FixedTask task = new FixedTask();
                task.setUserId(userId);
                task.setTitle(taskObj.get("title").getAsString());
                task.setDescription(taskObj.get("description").getAsString());
                
                // 解析时间
                String startTimeStr = taskObj.get("startTime").getAsString();
                String endTimeStr = taskObj.get("endTime").getAsString();
                
                LocalDate today = LocalDate.now();
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                
                task.setStartTime(LocalDateTime.of(today, 
                    java.time.LocalTime.parse(startTimeStr, timeFormatter)));
                task.setEndTime(LocalDateTime.of(today, 
                    java.time.LocalTime.parse(endTimeStr, timeFormatter)));
                
                task.setStatus("0"); // 未完成
                task.setDeleted(1); // 初始设为删除状态，等待用户确认
                task.setCreateTime(LocalDateTime.now());
                task.setUpdateTime(LocalDateTime.now());
                
                // 保存到数据库
                fixedTaskMapper.insert(task);
                
                // 构建返回给前端的任务详情
                Map<String, Object> taskDetail = new HashMap<>();
                taskDetail.put("id", task.getId());
                taskDetail.put("title", task.getTitle());
                taskDetail.put("description", task.getDescription());
                taskDetail.put("startTime", startTimeStr);
                taskDetail.put("endTime", endTimeStr);
                taskDetails.add(taskDetail);
                
                log.info("保存推荐任务: {} (ID: {})", task.getTitle(), task.getId());
            }
            
        } catch (Exception e) {
            log.error("解析AI返回JSON失败", e);
            throw new RuntimeException("解析AI返回数据失败: " + e.getMessage());
        }
        
        return taskDetails;
     }
     
     @Override
     public Result<String> confirmRecommendedTasks(List<TaskActionDTO> taskActions) {
         try {
             // 获取当前用户ID
             Long userId = BaseContext.getCurrentId();
             if (userId == null) {
                 return Result.error("用户未登录");
             }
             
             if (taskActions == null || taskActions.isEmpty()) {
                 return Result.error("请选择要操作的任务");
             }
             
             log.info("用户{}操作推荐任务，任务数量: {}", userId, taskActions.size());
             
             int commitCount = 0;
             int rejectCount = 0;
             System.out.println("taskActions:"+taskActions);
             for (TaskActionDTO taskAction : taskActions) {
                 Long taskId = taskAction.getTaskId();
                 String action = taskAction.getAction();
                 
                 // 查询任务是否存在且属于当前用户（需要查询deleted=1的记录）
                 FixedTask task = fixedTaskMapper.selectByIdIncludeDeleted(taskId, userId);
                 if (task != null && task.getDeleted() == 1) {
                     if ("commit".equals(action)) {
                         // 启用任务：将deleted状态改为0
                         FixedTask updateTask = new FixedTask();
                         updateTask.setId(taskId);
                         updateTask.setUserId(userId);
                         updateTask.setDeleted(0);
                         updateTask.setUpdateTime(LocalDateTime.now());
                         updateTask.setStatus("pending");
                         fixedTaskMapper.updateDeleted(updateTask);
                         commitCount++;
                         log.info("启用任务成功: {} (ID: {})", task.getTitle(), taskId);
                     } else if ("reject".equals(action)) {
                         // 拒绝任务：直接删除记录
                         fixedTaskMapper.deleteById(taskId, userId, LocalDateTime.now());
                         rejectCount++;
                         log.info("删除任务成功: {} (ID: {})", task.getTitle(), taskId);
                     }
                 } else {
                     log.warn("任务不存在或已处理: {}", taskId);
                 }
             }
             
             StringBuilder resultMsg = new StringBuilder();
             if (commitCount > 0) {
                 resultMsg.append("成功启用").append(commitCount).append("个任务");
             }
             if (rejectCount > 0) {
                 if (!resultMsg.isEmpty()) {
                     resultMsg.append("，");
                 }
                 resultMsg.append("成功删除").append(rejectCount).append("个任务");
             }
             
             if (commitCount > 0 || rejectCount > 0) {
                 log.info("任务操作完成 - 启用: {}个，删除: {}个", commitCount, rejectCount);
                 return Result.success(resultMsg.toString());
             } else {
                 return Result.error("没有可操作的任务");
             }
             
         } catch (Exception e) {
             log.error("操作推荐任务失败", e);
             return Result.error("操作任务失败: " + e.getMessage());
         }
     }
}
