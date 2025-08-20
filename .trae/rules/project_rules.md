# Cursor Rules for 碎时拾光 (Fragment Time Management) Project

## Project Overview
「碎时拾光」是一个基于Spring Boot + Vue3的前后端分离项目，专注于碎片时间管理、AI智能推荐和自动报告推送。核心理念是"从用户日程中主动挖掘价值"，通过分析用户空闲时间，推荐个性化的提升活动。

## Technology Stack
- **Backend**: Spring Boot 3.x + MySQL + Redis + MyBatis Plus
- **Frontend**: Vue3 + Vite + Element Plus + ECharts + Pinia
- **External Services**: SMTP邮件服务、AI推荐引擎（初期规则引擎，后期可扩展GPT）

## Code Style and Standards

### Java Code Style
- 使用4空格缩进，禁用Tab字符
- 遵循camelCase命名规范（变量、方法）
- 使用PascalCase命名类名
- 所有public方法必须添加JavaDoc注释，包含@param和@return
- 方法长度控制在30行以内，复杂逻辑拆分为私有方法
- 使用有意义的变量名，避免缩写（如用`userId`而非`uid`）

### Spring Boot Conventions
- 使用`@RestController`标注REST API控制器
- 使用`@Service`标注业务逻辑层
- 使用`@Mapper`标注MyBatis数据访问层
- 使用`@Component`标注工具类和配置类
- API路径遵循RESTful设计：`/api/{module}/{action}`
- 统一使用`Result<T>`包装所有API响应

### Package Structure (严格遵循)
```
com.lucius.fragmenttime
├── controller/          # REST API控制器
├── service/            # 业务逻辑接口
│   └── impl/           # 业务逻辑实现
├── mapper/             # MyBatis数据访问层
├── entity/             # 数据库实体类
├── dto/                # 数据传输对象
├── vo/                 # 视图对象
├── config/             # 配置类
├── utils/              # 工具类
├── common/             # 公共组件
│   ├── result/         # 统一响应结果
│   ├── exception/      # 异常处理
│   └── constant/       # 常量定义
└── schedule/           # 定时任务
```

### Database and Entity Guidelines
- 所有实体类使用Lombok注解：`@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
- 时间字段统一使用`LocalDateTime`，添加`@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")`
- 主键统一使用`Long id`，数据库使用自增策略
- 软删除字段命名为`deleted`（0-未删除，1-已删除）
- 创建时间字段：`createTime`，更新时间字段：`updateTime`
- 数据库表名使用下划线命名（如`fixed_task`），实体类使用驼峰命名（如`FixedTask`）

### Core Business Logic Guidelines

#### 1. 时间管理模块
- 固定任务（FixedTask）：必须校验时间冲突，使用`TimeConflictValidator`工具类
- 碎片任务（FragmentTask）：记录实际执行时间，关联推荐活动ID
- 空闲时间计算：使用`FreeTimeCalculator`工具类，结果缓存到Redis（24小时过期）

#### 2. AI推荐引擎
- 初期使用规则引擎（`RecommendationRuleEngine`），避免依赖外部AI服务
- 推荐逻辑：职业匹配 + 技能标签匹配 + 时长匹配
- 推荐结果缓存到Redis（1小时过期），key格式：`recommend:user:{userId}`
- 记录用户对推荐的反馈（完成率），用于优化推荐算法

#### 3. 报告生成与推送
- 使用`@Scheduled`定时任务：日报（每日20:00）、周报（每周日20:00）
- 报告内容使用JSON格式存储，便于前端解析和历史查看
- 邮件模板使用Thymeleaf，支持HTML格式和图表展示
- 邮件发送失败时使用`@Retryable`重试机制（最多3次）

### Redis Caching Strategy
- 用户信息缓存：`user:profile:{userId}`（1小时过期）
- 空闲时间缓存：`freetime:{userId}:{date}`（24小时过期）
- 推荐活动缓存：`recommend:user:{userId}`（1小时过期）
- 热门活动缓存：`hot:activities`（2小时过期，全用户共享）
- 使用`@Cacheable`、`@CacheEvict`注解管理缓存

### API Design Standards
- 统一响应格式：`Result<T>`包含code、message、data字段
- 成功响应：`Result.success(data)`
- 失败响应：`Result.error("错误信息")`
- 分页查询使用`PageResult<T>`，包含total、pageNum、pageSize、list字段
- 请求参数使用DTO，响应数据使用VO
- **每个接口完成后必须通过Swagger进行测试验证**，确保接口功能正常，参数校验正确，响应数据格式正确。
- 写完接口以后要在apidoc.md中按照格式要求写接口说明
- 每个接口的说明要包括接口路径、请求方法、请求参数、返回参数、示例
- 每个接口的示例要包括请求示例、响应示例
- 写完接口后不必启动项目

### Error Handling and Validation
- 使用`@GlobalExceptionHandler`统一异常处理
- 参数校验使用`@Valid`和`@Validated`注解
- 自定义业务异常：`BusinessException`，包含错误码和错误信息
- 数据库操作异常统一转换为业务异常
- 记录详细的错误日志，包含用户ID、操作时间、错误堆栈

### Security Guidelines
- 使用JWT进行用户认证，token有效期24小时
- 密码使用BCrypt加密，盐值长度12
- 敏感信息（如邮箱授权码）使用`@Value`从配置文件读取
- API接口添加访问频率限制（Redis + AOP实现）
- 所有用户输入进行XSS过滤和SQL注入防护

### Performance Optimization
- 数据库查询使用索引优化，避免全表扫描
- 大数据量查询使用分页，单页最大100条记录
- 频繁查询的数据使用Redis缓存
- 定时任务使用线程池异步执行，避免阻塞主线程
- 邮件发送使用异步队列，避免影响API响应时间

## Development Guidelines

### Adding New Features
1. **新增实体类**：先设计数据库表结构，再创建对应的Entity、DTO、VO
2. **新增API接口**：Controller → Service → Mapper的分层开发
3. **新增定时任务**：使用`@Scheduled`注解，配置cron表达式
4. **新增缓存策略**：评估数据访问频率，合理设置过期时间
5. **新增推荐规则**：在`RecommendationRuleEngine`中添加新的匹配逻辑

### Testing Standards
- 单元测试覆盖率要求80%以上
- 使用`@SpringBootTest`进行集成测试
- 使用`@MockBean`模拟外部依赖
- 时间相关逻辑使用固定时间进行测试
- 推荐算法使用多种用户画像进行测试

### Code Review Checklist
- [ ] 代码遵循项目命名规范
- [ ] 所有public方法有JavaDoc注释
- [ ] 数据库操作有事务管理
- [ ] 缓存策略合理，有过期时间
- [ ] 异常处理完整，有错误日志
- [ ] 时间处理使用LocalDateTime
- [ ] 敏感信息不硬编码
- [ ] API响应使用Result包装
- [ ] 单元测试覆盖核心逻辑
- [ ] 性能敏感操作有优化考虑

## Frontend Guidelines (Vue3)

### Component Structure
- 使用Composition API编写组件
- 组件文件命名使用PascalCase（如`TaskCalendar.vue`）
- 单文件组件结构：`<template>` → `<script setup>` → `<style scoped>`
- 复杂组件拆分为多个子组件，单个组件代码不超过200行

### State Management (Pinia)
- 用户信息：`useUserStore`
- 任务数据：`useTaskStore`
- 推荐活动：`useRecommendStore`
- 使用`$reset()`重置store状态
- 异步操作使用actions，同步计算使用getters

### API Integration
- 使用axios进行HTTP请求，统一配置baseURL和拦截器
- 请求拦截器自动添加JWT token
- 响应拦截器统一处理错误和token过期
- API方法命名：`getUserProfile`、`createFixedTask`、`getRecommendations`

### UI/UX Standards
- 使用Element Plus组件库，保持设计一致性
- 时间选择器使用`el-date-picker`，格式为"YYYY-MM-DD HH:mm"
- 表单验证使用Element Plus的rules配置
- 加载状态使用`el-loading`指令
- 错误提示使用`ElMessage.error()`
- 成功提示使用`ElMessage.success()`

## Deployment and Environment

### Development Environment
- 使用Docker Compose启动MySQL和Redis
- 后端使用`mvn spring-boot:run`启动
- 前端使用`npm run dev`启动
- 配置文件使用`application-dev.yml`

### Production Environment
- 后端打包为JAR文件部署
- 前端打包为静态文件，使用Nginx托管
- 数据库使用云服务（如阿里云RDS）
- Redis使用云缓存服务
- 配置文件使用`application-prod.yml`

## Common Pitfalls to Avoid

### Backend
- ❌ 不要在Controller中写业务逻辑
- ❌ 不要直接返回Entity对象，使用VO包装
- ❌ 不要忽略事务管理，特别是涉及多表操作
- ❌ 不要硬编码时间格式，使用常量定义
- ❌ 不要在循环中进行数据库查询
- ❌ 不要忽略缓存失效场景

### Frontend
- ❌ 不要在template中写复杂逻辑
- ❌ 不要直接修改props数据
- ❌ 不要忘记组件销毁时清理定时器
- ❌ 不要在computed中进行异步操作
- ❌ 不要忽略响应式数据的深度监听

### Time Management Logic
- ❌ 不要忽略时区问题，统一使用系统时区
- ❌ 不要忘记处理跨天任务（如23:00-01:00）
- ❌ 不要忽略节假日和周末的特殊处理
- ❌ 不要在空闲时间计算中包含睡眠时间

## AI Hallucination Prevention

### Code Generation Guidelines
- 明确指定使用的技术栈版本（Spring Boot 3.x, Vue 3.x）
- 提供具体的包名和类名，避免AI自创
- 引用现有的工具类和方法名
- 提供完整的方法签名和返回类型
- 指定具体的注解和配置

### Database Schema Consistency
- 严格按照已定义的表结构进行开发
- 字段名和类型必须与设计文档一致
- 外键关系和索引按照规范创建
- 不允许随意修改核心表结构

### API Contract Enforcement
- 严格按照定义的请求/响应格式
- 错误码和错误信息使用预定义常量
- 不允许随意修改已有API的签名
- 新增API必须遵循现有的命名规范

This project focuses on "fragment time management + AI recommendation + report push" with clear business logic and controlled implementation difficulty. The core highlight is "proactively mining value from user schedules" to better understand user improvement needs than traditional calendars.