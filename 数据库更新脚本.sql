-- 数据库表结构更新脚本
-- 用于将fixed_task表从repeat_type字段迁移到status字段

-- 1. 首先添加status字段（如果不存在）
ALTER TABLE `fixed_task` 
ADD COLUMN `status` VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '状态：pending-待开始，completed-已完成，abandoned-已放弃' 
AFTER `description`;

-- 2. 如果存在repeat_type字段，则删除它
-- 注意：在生产环境中，建议先备份数据
ALTER TABLE `fixed_task` 
DROP COLUMN IF EXISTS `repeat_type`;

-- 3. 更新现有数据的status字段（可选，根据业务需求调整）
-- 将所有现有任务设置为pending状态
UPDATE `fixed_task` 
SET `status` = 'pending' 
WHERE `status` IS NULL OR `status` = '';

-- 4. 验证表结构
DESC `fixed_task`;