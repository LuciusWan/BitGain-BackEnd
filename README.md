# BitGain - 碎时拾光

> 智能碎片时间管理与AI推荐系统

## 项目简介

「BitGain - 碎时拾光」是一个基于Spring Boot 3.x + Vue3的前后端分离项目，专注于碎片时间管理、AI智能推荐和自动报告推送。核心理念是"从用户日程中主动挖掘价值"，通过分析用户空闲时间，推荐个性化的提升活动，帮助用户充分利用每一分钟的碎片时间。

**队伍名称**: 塔司霆豪取灵码杯  
**团队成员**: 万丰阁、郝品越、孟金辉  
**参赛赛题**: 赛题3 - 智享生活  
**开发时间**: 2025年8月

## 核心概念

### 碎时拾光的含义
- **碎时**: 指日常生活中的碎片化时间，如通勤间隙、会议间隔、等待时间等
- **拾光**: 寓意"拾取光阴"，将零散的时间片段收集起来，转化为有价值的学习和提升机会
- **核心价值**: 不是简单的时间记录，而是主动发现和推荐适合当前时间段的提升活动

## 主要功能

### 1. 智能时间管理
- **固定任务管理**: 支持创建、编辑、删除日常固定任务，包含任务状态跟踪（待开始/已完成/已放弃）
- **今日目标设定**: 用户可设定每日目标，系统自动跟踪完成情况
- **碎片时间识别**: 自动分析用户日程，识别可利用的空闲时段
- **时间冲突检测**: 智能检测任务时间冲突，避免日程重叠

### 2. AI智能推荐引擎 ⭐ 核心亮点
- **智能日程生成**: AI深度分析用户职业背景、技能标签、个人目标和当前日程安排，自动生成个性化的碎片时间利用方案
- **多维度匹配算法**: 综合考虑用户空闲时间长度、当前技能水平、职业发展方向和学习偏好，精准推荐最适合的提升活动
- **实时流式推荐**: 采用SSE（Server-Sent Events）技术，AI实时分析并流式推送推荐内容，用户可即时查看生成过程
- **智能推荐确认**: 用户可选择接受或拒绝AI推荐的任务，系统持续学习用户偏好，不断优化推荐精度
- **动态时长适配**: 根据用户实际可用时间（5分钟、15分钟、30分钟等），智能匹配相应难度和类型的学习任务

### 3. 数据分析与智能报告 ⭐ 核心亮点
- **AI驱动的日报生成**: 每日20:00系统自动触发，AI分析用户当日任务完成情况、时间利用效率和目标达成度
- **个性化报告内容**: AI根据用户的完成率、时间分布、效率指标等数据，生成专属的分析报告和改进建议
- **准时邮件推送**: 定时任务精确在每晚8点执行，将包含数据可视化图表的HTML格式日报发送到用户邮箱
- **智能分析维度**: 深度分析任务完成率、碎片时间利用率、目标达成情况、学习效率趋势等多个维度
- **持续优化建议**: AI基于历史数据和完成情况，为用户提供个性化的时间管理和学习计划优化建议

### 4. 用户体验优化
- **JWT认证**: 安全的用户认证机制，支持token自动续期
- **RESTful API**: 标准化的API设计，支持前后端分离架构
- **Swagger文档**: 完整的API文档，便于开发和测试
- **个性化设置**: 支持用户自定义职业信息、技能标签和邮件订阅偏好

## 技术架构

### 后端技术栈
- **核心框架**: Spring Boot 3.5.4 + Java 17
- **数据库**: MySQL 8.0 (utf8mb4字符集)
- **ORM框架**: MyBatis 3.0.5
- **AI集成**: Spring AI 1.0.1 + OpenAI模型
- **安全认证**: Spring Security + JWT (jjwt 0.11.5)
- **邮件服务**: Spring Boot Mail + SMTP
- **定时任务**: Spring Schedule (Cron表达式)
- **API文档**: SpringDoc OpenAPI 3.0 (Swagger)
- **数据验证**: Spring Boot Validation
- **JSON处理**: Gson 2.10.1

### 前端技术栈
- **框架**: Vue 3.x + Composition API
- **构建工具**: Vite
- **UI组件库**: Element Plus
- **状态管理**: Pinia
- **HTTP客户端**: Axios
- **图表库**: ECharts

### 核心架构特点
- **前后端分离**: RESTful API设计，JWT无状态认证
- **分层架构**: Controller → Service → Mapper 三层架构
- **模块化设计**: 用户管理、任务管理、AI推荐、报告推送四大核心模块
- **流式处理**: SSE技术实现AI推荐的实时流式响应
- **定时任务**: 基于Spring Schedule的智能报告推送
- **安全设计**: BCrypt密码加密 + JWT令牌认证

## 快速开始

### 环境要求
- **Java**: 17+
- **Node.js**: 16+
- **MySQL**: 8.0+
- **Maven**: 3.6+

### 后端部署

1. **克隆后端代码**
   ```bash
   git clone https://github.com/LuciusWan/BitGain-BackEnd.git
   cd BitGain-BackEnd
   ```

2. **配置数据库**
   - 创建MySQL数据库 `fragment_time_db`
   - 执行 `建表语句Sqls.sql` 创建表结构
   - 可选：执行 `测试用户数据.sql` 导入测试数据

3. **配置应用**
   编辑 `src/main/resources/application.yml`：
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/fragment_time_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
       username: your_username
       password: your_password
       driver-class-name: com.mysql.cj.jdbc.Driver
     
     mail:
       host: smtp.163.com  # 或其他SMTP服务器
       port: 587
       username: your_email@163.com
       password: your_smtp_password
       properties:
         mail:
           smtp:
             auth: true
             starttls:
               enable: true
   
   # AI配置
   spring:
     ai:
       openai:
         api-key: your_openai_api_key
         base-url: https://api.openai.com
   
   # JWT配置
   bitgain:
     jwt:
       secret-key: your_jwt_secret_key
       ttl: 86400000  # 24小时
   ```

4. **编译和启动**
   ```bash
   # 编译项目
   mvn clean compile
   
   # 启动应用
   mvn spring-boot:run
   ```
   
   应用启动后访问：
   - **API接口**: http://localhost:8080
   - **Swagger文档**: http://localhost:8080/swagger-ui/index.html

### 前端部署

1. **克隆前端代码**
   ```bash
   git clone https://github.com/LuciusWan/BitGain-FrontEnd.git
   cd BitGain-FrontEnd
   ```

2. **安装依赖**
   ```bash
   npm install
   ```

3. **配置API地址**
   ```javascript
   // .env.production
   VITE_API_BASE_URL=http://your-backend-url:8081
   ```

4. **启动开发服务器**
   ```bash
   npm run dev
   ```
   
   前端应用访问：http://localhost:3000

5. **构建生产版本**
   ```bash
   npm run build
   ```

## 在线体验

🌐 **网站地址**: [http://39.104.77.78/](http://39.104.77.78/)

### 体验账号
- **用户名**: `123456`
- **密码**: `123456`

### 主要功能演示
1. **智能推荐**: 登录后体验基于AI的实时流式推荐
2. **任务管理**: 创建固定任务和今日目标，体验状态跟踪
3. **AI推荐确认**: 体验推荐任务的接受/拒绝机制
4. **智能日报**: 查看每日自动生成的个性化报告
5. **邮件推送**: 订阅日报，体验定时邮件推送功能

## 项目亮点



## 代码仓库

- **前端仓库**: [https://github.com/LuciusWan/BitGain-FrontEnd](https://github.com/LuciusWan/BitGain-FrontEnd)
- **后端仓库**: [https://github.com/LuciusWan/BitGain-BackEnd](https://github.com/LuciusWan/BitGain-BackEnd)

## API文档

### 核心接口概览

#### 用户认证模块
- `POST /user/register` - 用户注册（用户名、密码、手机号）
- `POST /user/login` - 用户登录（返回JWT token）
- `GET /user/info` - 获取当前用户信息（需要JWT认证）
- `PUT /user/update` - 更新用户信息（职业、技能标签等）

#### 任务管理模块
- `GET /fixed-task/list` - 获取用户固定任务列表
- `POST /fixed-task` - 创建固定任务（支持时间冲突检测）
- `PUT /fixed-task` - 更新固定任务状态
- `DELETE /fixed-task/{id}` - 删除固定任务
- `GET /today-goal/list` - 获取今日目标列表
- `POST /today-goal` - 创建今日目标

#### AI推荐模块
- `GET /bitgain-design/recommend` - 获取AI推荐（SSE流式响应）
- `POST /bitgain-design/confirm` - 确认推荐任务（接受/拒绝）

#### 报告推送模块
- 定时任务自动生成日报（每日20:00）
- 邮件推送个性化报告

### 完整API文档
- **在线文档**: [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- **详细说明**: 查看项目根目录下的 `apidoc.md` 文件

## 贡献指南

### 开发规范
1. **代码风格**: 遵循项目既定的代码规范，使用4空格缩进
2. **命名规范**: 类名使用PascalCase，方法和变量使用camelCase
3. **注释要求**: 所有public方法必须添加JavaDoc注释
4. **测试覆盖**: 新增功能需要编写对应的单元测试
5. **数据库**: 遵循软删除原则，使用`deleted`字段标记

### 技术要求
- **后端**: Spring Boot 3.x + MyBatis + MySQL
- **前端**: Vue 3.x + Element Plus + Pinia
- **AI集成**: Spring AI框架 + OpenAI模型
- **认证**: JWT + Spring Security

### 提交流程
1. Fork 项目到个人仓库
2. 创建功能分支 (`git checkout -b feature/新功能名称`)
3. 完成开发并测试
4. 提交更改 (`git commit -m 'feat: 添加新功能描述'`)
5. 推送到分支 (`git push origin feature/新功能名称`)
6. 创建 Pull Request

### 问题反馈
如果您在使用过程中遇到问题，请通过以下方式联系我们：
- **GitHub Issues**: 提交bug报告或功能建议
- **邮箱联系**: 18099488938@163.com
- **项目文档**: 查看 `exception.md` 了解常见问题解决方案


## 数据库设计

### 核心数据表
- **user**: 用户基本信息、职业技能、邮件订阅设置
- **fixed_task**: 固定任务管理，支持状态跟踪（pending/completed/abandoned）
- **today_goal**: 每日目标设定和完成情况
- **recommend_activity**: AI推荐活动库，包含分类、难度、适用职业等
- **fragment_task**: 碎片任务执行记录

### 设计特点
- **软删除**: 所有表使用`deleted`字段实现软删除
- **时间戳**: 统一的`create_time`和`update_time`字段
- **字符集**: utf8mb4字符集，支持emoji和特殊字符
- **索引优化**: 针对查询频繁的字段建立合适的索引

## 系统监控

### 智能定时任务系统 ⭐ 技术亮点
- **精准定时执行**: 使用Spring Boot @Scheduled注解，Cron表达式 `0 0 20 * * ?` 确保每日20:00准时触发
- **AI驱动日报生成**: 定时任务自动调用AI接口，分析用户当日数据，生成个性化报告内容
- **异步邮件推送**: 采用Spring异步机制，避免邮件发送阻塞主线程，支持批量用户并发处理
- **智能失败重试**: @Retryable注解实现邮件发送失败自动重试（最多3次），确保推送成功率
- **实时数据统计**: 动态计算任务完成率、碎片时间利用效率、目标达成度等关键指标

### 日志记录
- **操作日志**: 记录用户关键操作和系统状态
- **错误日志**: 详细的异常堆栈信息，便于问题排查
- **性能监控**: 接口响应时间和数据库查询性能


## 联系我们

### 开发团队
- **团队名称**: 塔司霆豪取灵码杯
- **项目负责人**: Lucius
- **开发时间**: 2025年8月

### 联系方式
- **技术支持**: 18099488938@163.com
- **项目反馈**: 通过GitHub Issues提交
- **商务合作**: 请发送邮件详细说明合作意向

---

**感谢您对BitGain项目的关注和支持！** 🚀

如果这个项目对您有帮助，请给我们一个 ⭐ Star，这将是对我们最大的鼓励！
