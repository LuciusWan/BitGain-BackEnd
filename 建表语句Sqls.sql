-- =====================================================
-- 碎时拾光 - 数据库建表语句
-- 数据库名称: fragment_time_db
-- 字符集: utf8mb4
-- 排序规则: utf8mb4_unicode_ci
-- 创建时间: 2025-01-20
-- =====================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `fragment_time_db` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE `fragment_time_db`;

-- =====================================================
-- 1. 用户表 (user)
-- 功能: 存储用户基本信息和账户设置
-- =====================================================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID，主键',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名，唯一',
  `password` VARCHAR(255) NOT NULL COMMENT '密码，BCrypt加密',
  `email` VARCHAR(100) NOT NULL COMMENT '邮箱，唯一',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `profession` VARCHAR(100) DEFAULT NULL COMMENT '职业',
  `skills` TEXT DEFAULT NULL COMMENT '技能标签，逗号分隔',
  `goals` TEXT DEFAULT NULL COMMENT '提升目标',
  `email_subscribe` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '邮件订阅开关：0-关闭，1-开启',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `idx_phone` (`phone`),
  KEY `idx_profession` (`profession`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- =====================================================
-- 2. 固定任务表 (fixed_task)
-- 功能: 存储用户的固定日程安排
-- =====================================================
DROP TABLE IF EXISTS `fixed_task`;
CREATE TABLE `fixed_task` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '任务ID，主键',
  `user_id` BIGINT NOT NULL COMMENT '用户ID，外键',
  `title` VARCHAR(200) NOT NULL COMMENT '任务标题',
  `start_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME NOT NULL COMMENT '结束时间',
  `description` TEXT DEFAULT NULL COMMENT '任务描述',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '状态：pending-待开始，completed-已完成，abandoned-已放弃',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_deleted` (`deleted`),
  KEY `idx_user_time` (`user_id`, `start_time`, `end_time`),
  CONSTRAINT `fk_fixed_task_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='固定任务表';

-- =====================================================
-- 3. 推荐活动表 (recommend_activity)
-- 功能: 存储系统推荐的活动库
-- =====================================================
DROP TABLE IF EXISTS `recommend_activity`;
CREATE TABLE `recommend_activity` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '活动ID，主键',
  `title` VARCHAR(200) NOT NULL COMMENT '活动标题',
  `description` TEXT NOT NULL COMMENT '活动描述',
  `category` VARCHAR(50) NOT NULL COMMENT '活动分类：学习提升、健康运动、创意娱乐、生活技能等',
  `duration` INT NOT NULL COMMENT '建议时长（分钟）',
  `difficulty` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '难度等级：1-非常简单，2-简单，3-中等，4-困难，5-非常困难',
  `skills_required` VARCHAR(500) DEFAULT NULL COMMENT '所需技能，逗号分隔',
  `profession_match` VARCHAR(200) DEFAULT NULL COMMENT '适合职业，逗号分隔',
  `popularity` INT NOT NULL DEFAULT 0 COMMENT '受欢迎程度（完成次数）',
  `tags` VARCHAR(500) DEFAULT NULL COMMENT '标签，逗号分隔',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_title` (`title`),
  KEY `idx_category` (`category`),
  KEY `idx_duration` (`duration`),
  KEY `idx_difficulty` (`difficulty`),
  KEY `idx_profession_match` (`profession_match`),
  KEY `idx_popularity` (`popularity`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='推荐活动表';

-- =====================================================
-- 4. 碎片任务表 (fragment_task)
-- 功能: 记录用户完成的碎片活动
-- =====================================================
DROP TABLE IF EXISTS `fragment_task`;
CREATE TABLE `fragment_task` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '任务ID，主键',
  `user_id` BIGINT NOT NULL COMMENT '用户ID，外键',
  `activity_id` BIGINT DEFAULT NULL COMMENT '推荐活动ID，外键，可为空（用户自定义活动）',
  `title` VARCHAR(200) NOT NULL COMMENT '活动标题',
  `start_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME NOT NULL COMMENT '结束时间',
  `duration` INT NOT NULL COMMENT '实际时长（分钟）',
  `status` VARCHAR(20) NOT NULL DEFAULT 'completed' COMMENT '状态：completed-已完成，abandoned-已放弃',
  `feedback` TINYINT(1) DEFAULT NULL COMMENT '用户反馈：1-很差，2-较差，3-一般，4-较好，5-很好',
  `notes` TEXT DEFAULT NULL COMMENT '用户备注',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_status` (`status`),
  KEY `idx_deleted` (`deleted`),
  KEY `idx_user_time` (`user_id`, `start_time`),
  CONSTRAINT `fk_fragment_task_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_fragment_task_activity` FOREIGN KEY (`activity_id`) REFERENCES `recommend_activity` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='碎片任务表';

-- =====================================================
-- 5. 用户偏好表 (user_preference)
-- 功能: 记录用户对推荐活动的偏好
-- =====================================================
DROP TABLE IF EXISTS `user_preference`;
CREATE TABLE `user_preference` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '偏好ID，主键',
  `user_id` BIGINT NOT NULL COMMENT '用户ID，外键',
  `activity_id` BIGINT NOT NULL COMMENT '活动ID，外键',
  `preference_score` DECIMAL(3,2) NOT NULL DEFAULT 0.00 COMMENT '偏好分数：0.00-1.00，越高表示越喜欢',
  `completion_count` INT NOT NULL DEFAULT 0 COMMENT '完成次数',
  `total_recommended` INT NOT NULL DEFAULT 0 COMMENT '推荐总次数',
  `avg_feedback` DECIMAL(3,2) DEFAULT NULL COMMENT '平均反馈分数',
  `last_completed` DATETIME DEFAULT NULL COMMENT '最后完成时间',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_activity` (`user_id`, `activity_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_activity_id` (`activity_id`),
  CONSTRAINT `fk_user_preference_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_user_preference_activity` FOREIGN KEY (`activity_id`) REFERENCES `recommend_activity` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户偏好表';

-- =====================================================
-- 6. 报告表 (report)
-- 功能: 存储生成的日报和周报
-- =====================================================
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '报告ID，主键',
  `user_id` BIGINT NOT NULL COMMENT '用户ID，外键',
  `report_type` VARCHAR(20) NOT NULL COMMENT '报告类型：daily-日报，weekly-周报',
  `report_date` DATE NOT NULL COMMENT '报告日期',
  `title` VARCHAR(200) NOT NULL COMMENT '报告标题',
  `content` JSON NOT NULL COMMENT '报告内容，JSON格式存储',
  `statistics` JSON DEFAULT NULL COMMENT '统计数据，JSON格式存储',
  `email_sent` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已发送邮件：0-未发送，1-已发送',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_report` (`user_id`, `report_type`, `report_date`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_report_type` (`report_type`),
  KEY `idx_report_date` (`report_date`),
  CONSTRAINT `fk_report_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报告表';

-- =====================================================
-- 7. 邮件日志表 (email_log)
-- 功能: 记录邮件发送历史
-- =====================================================
DROP TABLE IF EXISTS `email_log`;
CREATE TABLE `email_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID，主键',
  `user_id` BIGINT NOT NULL COMMENT '用户ID，外键',
  `email_type` VARCHAR(50) NOT NULL COMMENT '邮件类型：register-注册验证，report-报告推送，notification-通知',
  `recipient` VARCHAR(100) NOT NULL COMMENT '收件人邮箱',
  `subject` VARCHAR(200) NOT NULL COMMENT '邮件主题',
  `content` TEXT DEFAULT NULL COMMENT '邮件内容',
  `send_status` VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '发送状态：pending-待发送，success-成功，failed-失败',
  `send_time` DATETIME DEFAULT NULL COMMENT '发送时间',
  `error_message` TEXT DEFAULT NULL COMMENT '错误信息',
  `retry_count` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '重试次数',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_email_type` (`email_type`),
  KEY `idx_send_status` (`send_status`),
  KEY `idx_send_time` (`send_time`),
  CONSTRAINT `fk_email_log_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='邮件日志表';

-- =====================================================
-- 初始化推荐活动数据
-- =====================================================
INSERT INTO `recommend_activity` (`title`, `description`, `category`, `duration`, `difficulty`, `skills_required`, `profession_match`, `popularity`, `tags`) VALUES
-- 学习提升类
('阅读技术文章', '阅读最新的技术博客或文章，了解行业动态', '学习提升', 15, 2, '阅读理解,技术敏感度', '程序员,产品经理,设计师', 0, '技术,阅读,学习'),
('观看在线教程', '观看短视频教程学习新技能', '学习提升', 20, 2, '学习能力,专注力', '程序员,设计师,运营', 0, '教程,技能,学习'),
('练习英语口语', '通过APP练习英语发音和对话', '学习提升', 10, 3, '语言能力,口语表达', '所有职业', 0, '英语,口语,语言'),
('背诵单词', '使用记忆法背诵英语单词', '学习提升', 15, 2, '记忆力,语言能力', '所有职业', 0, '英语,单词,记忆'),
('学习新编程语言', '通过在线平台学习新的编程语言基础', '学习提升', 30, 4, '逻辑思维,编程基础', '程序员,数据分析师', 0, '编程,技术,学习'),

-- 健康运动类
('室内拉伸运动', '进行简单的拉伸运动，缓解久坐疲劳', '健康运动', 10, 1, '身体协调性', '所有职业', 0, '拉伸,健康,运动'),
('深呼吸冥想', '进行深呼吸和简单冥想，放松身心', '健康运动', 5, 1, '专注力,自控力', '所有职业', 0, '冥想,放松,健康'),
('眼保健操', '做眼保健操，缓解眼部疲劳', '健康运动', 5, 1, '身体协调性', '程序员,设计师,文员', 0, '眼保健,健康,护眼'),
('室内有氧运动', '进行简单的有氧运动，如原地跑步', '健康运动', 15, 2, '体力,协调性', '所有职业', 0, '有氧,运动,健康'),
('颈椎保健操', '做颈椎保健操，预防颈椎病', '健康运动', 8, 1, '身体协调性', '程序员,设计师,文员', 0, '颈椎,保健,健康'),

-- 创意娱乐类
('画简笔画', '用纸笔画简单的简笔画，发挥创意', '创意娱乐', 10, 2, '创意思维,绘画基础', '设计师,艺术家', 0, '绘画,创意,艺术'),
('写微小说', '创作100字以内的微小说', '创意娱乐', 15, 3, '文字表达,创意思维', '编辑,作家,运营', 0, '写作,创意,文学'),
('听音乐放松', '听喜欢的音乐，放松心情', '创意娱乐', 10, 1, '音乐欣赏', '所有职业', 0, '音乐,放松,娱乐'),
('练习书法', '练习毛笔字或硬笔书法', '创意娱乐', 20, 3, '书法基础,专注力', '所有职业', 0, '书法,艺术,练习'),
('折纸手工', '学习简单的折纸技巧', '创意娱乐', 12, 2, '手工技巧,空间想象', '所有职业', 0, '手工,折纸,创意'),

-- 生活技能类
('整理桌面', '整理工作桌面，提高工作效率', '生活技能', 8, 1, '整理能力', '所有职业', 0, '整理,效率,生活'),
('学习理财知识', '阅读理财文章，学习投资基础', '生活技能', 20, 3, '数学基础,逻辑思维', '所有职业', 0, '理财,投资,学习'),
('练习打字', '通过打字软件提高打字速度', '生活技能', 15, 2, '手指灵活性,专注力', '程序员,文员,编辑', 0, '打字,技能,效率'),
('学习摄影技巧', '通过手机学习基础摄影构图', '生活技能', 25, 3, '审美能力,观察力', '设计师,运营,摄影师', 0, '摄影,技巧,艺术'),
('制定计划', '为明天或下周制定详细计划', '生活技能', 10, 2, '规划能力,逻辑思维', '所有职业', 0, '计划,规划,效率'),

-- 思维训练类
('数独游戏', '完成一道数独题目，锻炼逻辑思维', '思维训练', 15, 3, '逻辑思维,数学基础', '程序员,数据分析师', 0, '数独,逻辑,游戏'),
('记忆训练', '使用记忆法训练记忆力', '思维训练', 10, 2, '记忆力,专注力', '所有职业', 0, '记忆,训练,思维'),
('脑筋急转弯', '思考和解答脑筋急转弯题目', '思维训练', 8, 2, '创意思维,逻辑思维', '所有职业', 0, '思维,创意,游戏'),
('思维导图', '为某个主题制作简单的思维导图', '思维训练', 20, 3, '逻辑思维,归纳能力', '所有职业', 0, '思维导图,逻辑,整理'),
('快速阅读训练', '练习快速阅读技巧，提高阅读效率', '思维训练', 12, 3, '阅读理解,专注力', '所有职业', 0, '阅读,速度,训练');

-- =====================================================
-- 创建视图
-- =====================================================

-- 用户活动统计视图
CREATE VIEW `v_user_activity_stats` AS
SELECT 
    u.id AS user_id,
    u.username,
    u.profession,
    COUNT(ft.id) AS total_fragments,
    SUM(ft.duration) AS total_duration,
    AVG(ft.feedback) AS avg_feedback,
    COUNT(DISTINCT DATE(ft.start_time)) AS active_days
FROM `user` u
LEFT JOIN `fragment_task` ft ON u.id = ft.user_id AND ft.deleted = 0
WHERE u.deleted = 0
GROUP BY u.id, u.username, u.profession;

-- 活动受欢迎程度视图
CREATE VIEW `v_activity_popularity` AS
SELECT 
    ra.id AS activity_id,
    ra.title,
    ra.category,
    ra.duration,
    ra.difficulty,
    COUNT(ft.id) AS completion_count,
    AVG(ft.feedback) AS avg_feedback,
    ra.popularity
FROM `recommend_activity` ra
LEFT JOIN `fragment_task` ft ON ra.id = ft.activity_id AND ft.deleted = 0
WHERE ra.deleted = 0
GROUP BY ra.id, ra.title, ra.category, ra.duration, ra.difficulty, ra.popularity
ORDER BY completion_count DESC, avg_feedback DESC;

-- =====================================================
-- 创建存储过程
-- =====================================================

-- 计算用户空闲时间的存储过程
DELIMITER //
CREATE PROCEDURE `sp_calculate_free_time`(
    IN p_user_id BIGINT,
    IN p_date DATE
)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_start_time DATETIME;
    DECLARE v_end_time DATETIME;
    DECLARE v_last_end DATETIME DEFAULT CONCAT(p_date, ' 08:00:00');
    DECLARE v_day_end DATETIME DEFAULT CONCAT(p_date, ' 22:00:00');
    
    -- 游标声明
    DECLARE task_cursor CURSOR FOR 
        SELECT start_time, end_time 
        FROM fixed_task 
        WHERE user_id = p_user_id 
          AND DATE(start_time) = p_date 
          AND deleted = 0
        ORDER BY start_time;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    -- 创建临时表存储空闲时间段
    DROP TEMPORARY TABLE IF EXISTS temp_free_time;
    CREATE TEMPORARY TABLE temp_free_time (
        start_time DATETIME,
        end_time DATETIME,
        duration INT
    );
    
    -- 打开游标
    OPEN task_cursor;
    
    read_loop: LOOP
        FETCH task_cursor INTO v_start_time, v_end_time;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        -- 如果当前任务开始时间晚于上一个结束时间，则存在空闲时间
        IF v_start_time > v_last_end THEN
            INSERT INTO temp_free_time (start_time, end_time, duration)
            VALUES (v_last_end, v_start_time, TIMESTAMPDIFF(MINUTE, v_last_end, v_start_time));
        END IF;
        
        -- 更新最后结束时间
        SET v_last_end = GREATEST(v_last_end, v_end_time);
    END LOOP;
    
    -- 关闭游标
    CLOSE task_cursor;
    
    -- 检查最后一个任务结束后到一天结束是否还有空闲时间
    IF v_last_end < v_day_end THEN
        INSERT INTO temp_free_time (start_time, end_time, duration)
        VALUES (v_last_end, v_day_end, TIMESTAMPDIFF(MINUTE, v_last_end, v_day_end));
    END IF;
    
    -- 返回结果
    SELECT * FROM temp_free_time WHERE duration >= 5 ORDER BY start_time;
    
    -- 清理临时表
    DROP TEMPORARY TABLE temp_free_time;
END //
DELIMITER ;

-- =====================================================
-- 创建触发器
-- =====================================================

-- 更新推荐活动受欢迎程度的触发器
DELIMITER //
CREATE TRIGGER `tr_update_activity_popularity`
AFTER INSERT ON `fragment_task`
FOR EACH ROW
BEGIN
    IF NEW.activity_id IS NOT NULL AND NEW.status = 'completed' THEN
        UPDATE `recommend_activity` 
        SET `popularity` = `popularity` + 1
        WHERE `id` = NEW.activity_id;
    END IF;
END //
DELIMITER ;

-- 更新用户偏好的触发器
DELIMITER //
CREATE TRIGGER `tr_update_user_preference`
AFTER INSERT ON `fragment_task`
FOR EACH ROW
BEGIN
    IF NEW.activity_id IS NOT NULL AND NEW.status = 'completed' THEN
        INSERT INTO `user_preference` (user_id, activity_id, completion_count, total_recommended, avg_feedback, last_completed)
        VALUES (NEW.user_id, NEW.activity_id, 1, 1, NEW.feedback, NEW.start_time)
        ON DUPLICATE KEY UPDATE
            completion_count = completion_count + 1,
            avg_feedback = IF(NEW.feedback IS NOT NULL, 
                             (IFNULL(avg_feedback, 0) * completion_count + NEW.feedback) / (completion_count + 1),
                             avg_feedback),
            last_completed = NEW.start_time,
            update_time = CURRENT_TIMESTAMP;
    END IF;
END //
DELIMITER ;

-- =====================================================
-- 创建索引（补充）
-- =====================================================

-- 为JSON字段创建虚拟列和索引（MySQL 5.7+）
ALTER TABLE `report` 
ADD COLUMN `report_summary` VARCHAR(500) GENERATED ALWAYS AS (JSON_UNQUOTE(JSON_EXTRACT(content, '$.summary'))) STORED,
ADD INDEX `idx_report_summary` (`report_summary`);

-- 为时间范围查询优化的复合索引
CREATE INDEX `idx_fragment_task_user_date` ON `fragment_task` (`user_id`, `start_time`, `status`);
CREATE INDEX `idx_fixed_task_user_date` ON `fixed_task` (`user_id`, `start_time`, `end_time`);

-- =====================================================
-- 数据库配置优化
-- =====================================================

-- 设置MySQL配置参数（需要在my.cnf中配置）
/*
[mysqld]
# 字符集设置
character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci

# InnoDB设置
innodb_buffer_pool_size = 1G
innodb_log_file_size = 256M
innodb_flush_log_at_trx_commit = 2
innodb_flush_method = O_DIRECT

# 查询缓存
query_cache_type = 1
query_cache_size = 256M

# 连接设置
max_connections = 1000
max_connect_errors = 10000

# 慢查询日志
slow_query_log = 1
slow_query_log_file = /var/log/mysql/slow.log
long_query_time = 2

# 二进制日志
log-bin = mysql-bin
binlog_format = ROW
expire_logs_days = 7
*/

-- =====================================================
-- 数据库用户和权限设置
-- =====================================================

-- 创建应用程序数据库用户
CREATE USER IF NOT EXISTS 'fragment_app'@'%' IDENTIFIED BY 'FragmentTime2025!';
GRANT SELECT, INSERT, UPDATE, DELETE ON fragment_time_db.* TO 'fragment_app'@'%';

-- 创建只读用户（用于报表查询）
CREATE USER IF NOT EXISTS 'fragment_readonly'@'%' IDENTIFIED BY 'ReadOnly2025!';
GRANT SELECT ON fragment_time_db.* TO 'fragment_readonly'@'%';

-- 刷新权限
FLUSH PRIVILEGES;

-- =====================================================
-- 数据备份脚本示例
-- =====================================================
/*
#!/bin/bash
# 数据库备份脚本

DB_NAME="fragment_time_db"
DB_USER="root"
DB_PASS="your_password"
BACKUP_DIR="/backup/mysql"
DATE=$(date +"%Y%m%d_%H%M%S")

# 创建备份目录
mkdir -p $BACKUP_DIR

# 执行备份
mysqldump -u$DB_USER -p$DB_PASS --single-transaction --routines --triggers $DB_NAME > $BACKUP_DIR/${DB_NAME}_$DATE.sql

# 压缩备份文件
gzip $BACKUP_DIR/${DB_NAME}_$DATE.sql

# 删除7天前的备份
find $BACKUP_DIR -name "${DB_NAME}_*.sql.gz" -mtime +7 -delete

echo "Database backup completed: ${DB_NAME}_$DATE.sql.gz"
*/

-- =====================================================
-- 性能监控查询
-- =====================================================

-- 查看表大小
/*
SELECT 
    table_name AS '表名',
    ROUND(((data_length + index_length) / 1024 / 1024), 2) AS '大小(MB)',
    table_rows AS '行数'
FROM information_schema.tables 
WHERE table_schema = 'fragment_time_db'
ORDER BY (data_length + index_length) DESC;
*/

-- 查看慢查询
/*
SELECT 
    query_time,
    lock_time,
    rows_sent,
    rows_examined,
    sql_text
FROM mysql.slow_log 
WHERE start_time >= DATE_SUB(NOW(), INTERVAL 1 DAY)
ORDER BY query_time DESC
LIMIT 10;
*/

-- =====================================================
-- 完成建表
-- =====================================================

SELECT 'Database fragment_time_db created successfully!' AS message;
SELECT COUNT(*) AS table_count FROM information_schema.tables WHERE table_schema = 'fragment_time_db';
SELECT 'Initial data inserted successfully!' AS message;