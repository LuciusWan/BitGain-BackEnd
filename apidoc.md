# API文档

## JWT认证说明

对于需要认证的接口，请在请求头中添加Authorization字段，格式如下：

```
Authorization: Bearer <your_jwt_token>
```

示例：
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIxLCJ1c2VybmFtZSI6InRlc3R1c2VyIiwiZXhwIjoxNzU1NjI5NzczfQ.Bm4nL8cLNkCkBeXL7QUE3cWrQDVru1Av8cqq05xuDn0
```

**注意：** 
- Bearer和token之间必须有一个空格
- token从登录接口的返回结果中获取
- 需要认证的接口包括：`/user/info` 等

## 默认返回参数

| 参数名     | 类型     | 说明                |
| ------- | ------ | ----------------- |
| code    | int    | 0代表正常返回,1代表有异常    |
| message | string | 正常为null,有异常就是异常信息 |
| data    | object | 返回的数据             |
## 用户端接口
### 注册接口

| 地址     | 请求方法 |
| ------ | ---- |
| /user/register | POST |
#### 请求参数

| 参数名      | 类型     | 说明       |
| -------- | ------ | -------- |
| username | string | 用户名      |
| password | string | 密码       |
| phone    | string | 手机号      |
#### 返回参数

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| data     | string | 注册结果信息 |
#### 示例
```json
{
  "code": 0,
  "message": null,
  "data": "注册成功"
}
```

### 登录

| 地址     | 请求方法 |
| ------ | ---- |
| /user/login | POST |
#### 请求参数

| 参数名      | 类型     | 说明       |
| -------- | ------ | -------- |
| username | string | 用户名      |
| password | string | 密码       |
#### 返回参数

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| userId       | int    | 用户id   |
| username | string | 用户名    |
| token    | string | jwt令牌  |
#### 示例
```json
{
  "code": 0,
  "message": null,
  "data": {
    "userId": 21,
    "username": "testuser",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIxLCJ1c2VybmFtZSI6InRlc3R1c2VyIiwiZXhwIjoxNzU1NjI5NzczfQ.Bm4nL8cLNkCkBeXL7QUE3cWrQDVru1Av8cqq05xuDn0"
  }
}
```

### 获取当前用户信息

| 地址     | 请求方法 |
| ------ | ---- |
| /user/info | GET |
#### 请求参数

无需参数（通过JWT令牌获取用户ID）

#### 返回参数

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| username | string | 用户名    |
| phone    | string | 手机号    |
| email    | string | 邮箱     |
| profession | string | 职业     |
| skills   | string | 技能标签（逗号分隔） |
| goals    | string | 提升目标   |
| emailSubscribe | int | 邮件订阅开关（1:开启,0:关闭） |
#### 示例
```json
{
  "code": 0,
  "message": null,
  "data": {
    "username": "testuser",
    "phone": "13800138001",
    "email": "user@example.com",
    "profession": "软件工程师",
    "skills": "Java,Spring Boot,MySQL",
    "goals": "学习微服务架构,提升系统设计能力",
    "emailSubscribe": 1
  }
}
```

### 更新用户信息

| 地址     | 请求方法 |
| ------ | ---- |
| /user/info | PUT |
#### 请求参数

| 参数名      | 类型     | 说明       |
| -------- | ------ | -------- |
| username | string | 用户名（必填，2-20个字符）      |
| phone    | string | 手机号（必填，11位数字）      |
| email    | string | 邮箱（必填，有效邮箱格式）       |
| profession | string | 职业（可选）       |
| skills   | string | 技能标签（可选，逗号分隔）       |
| goals    | string | 提升目标（可选）       |
| emailSubscribe | int | 邮件订阅开关（可选，1:开启,0:关闭） |
#### 返回参数

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| data     | string | 更新结果信息 |
#### 示例

**请求示例：**
```json
{
  "username": "newusername",
  "phone": "13900139001",
  "email": "newemail@example.com",
  "profession": "产品经理",
  "skills": "产品设计,用户研究,数据分析",
  "goals": "学习AI产品设计,提升数据驱动决策能力",
  "emailSubscribe": 1
}
```

**响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": "更新成功"
}
```

**错误响应示例：**
```json
{
  "code": 1,
  "message": "用户名已存在",
  "data": null
}
```

## 固定任务管理接口

### 创建固定任务

| 地址     | 请求方法 |
| ------ | ---- |
| /api/fixed-task | POST |

#### 请求参数

| 参数名      | 类型     | 说明       |
| -------- | ------ | -------- |
| title | string | 任务标题（必填，1-100个字符）      |
| startTime | string | 开始时间（必填，格式：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'）      |
| endTime | string | 结束时间（必填，格式：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'）       |
| description | string | 任务描述（可选，最多500个字符）       |
| status | string | 状态（必填，可选值：pending-待开始，completed-已完成，abandoned-已放弃）       |

#### 返回参数

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| id | long | 任务ID |
| userId | long | 用户ID |
| title | string | 任务标题 |
| startTime | string | 开始时间 |
| endTime | string | 结束时间 |
| description | string | 任务描述 |
| status | string | 状态 |
| createTime | string | 创建时间 |
| updateTime | string | 更新时间 |

#### 示例

**请求示例：**
```json
{
  "title": "晨间锻炼",
  "startTime": "2024-01-15T07:00:00.000Z",
  "endTime": "2024-01-15T08:00:00.000Z",
  "description": "每日晨跑，保持身体健康",
  "status": "pending"
}
```

**响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": {
    "id": 1,
    "userId": 21,
    "title": "晨间锻炼",
    "startTime": "2024-01-15T07:00:00.000Z",
    "endTime": "2024-01-15T08:00:00.000Z",
    "description": "每日晨跑，保持身体健康",
    "status": "pending",
    "createTime": "2024-01-15T06:00:00.000Z",
    "updateTime": "2024-01-15T06:00:00.000Z"
  }
}
```

### 删除固定任务

| 地址     | 请求方法 |
| ------ | ---- |
| /api/fixed-task/{id} | DELETE |

#### 请求参数

| 参数名      | 类型     | 说明       |
| -------- | ------ | -------- |
| id | long | 任务ID（路径参数）      |

#### 返回参数

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| data | null | 删除成功时为null |

#### 示例

**请求示例：**
```
DELETE /api/fixed-task/1
```

**响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": null
}
```

### 更新固定任务

| 地址     | 请求方法 |
| ------ | ---- |
| /api/fixed-task | PUT |

#### 请求参数

| 参数名      | 类型     | 说明       |
| -------- | ------ | -------- |
| id | long | 任务ID（必填）      |
| title | string | 任务标题（必填，1-100个字符）      |
| startTime | string | 开始时间（必填，格式：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'）      |
| endTime | string | 结束时间（必填，格式：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'）       |
| description | string | 任务描述（可选，最多500个字符）       |
| status | string | 状态（必填，可选值：pending-待开始，completed-已完成，abandoned-已放弃）       |

#### 返回参数

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| id | long | 任务ID |
| userId | long | 用户ID |
| title | string | 任务标题 |
| startTime | string | 开始时间 |
| endTime | string | 结束时间 |
| description | string | 任务描述 |
| status | string | 状态 |
| createTime | string | 创建时间 |
| updateTime | string | 更新时间 |

#### 示例

**请求示例：**
```json
{
  "id": 1,
  "title": "晚间锻炼",
  "startTime": "2024-01-15T19:00:00.000Z",
  "endTime": "2024-01-15T20:00:00.000Z",
  "description": "每日晚间健身，增强体质",
  "status": "pending"
}
```

**响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": {
    "id": 1,
    "userId": 21,
    "title": "晚间锻炼",
    "startTime": "2024-01-15T19:00:00.000Z",
    "endTime": "2024-01-15T20:00:00.000Z",
    "description": "每日晚间健身，增强体质",
    "status": "pending",
    "createTime": "2024-01-15T06:00:00.000Z",
    "updateTime": "2024-01-15T18:30:00.000Z"
  }
}
```

### 查询固定任务详情

| 地址     | 请求方法 |
| ------ | ---- |
| /api/fixed-task/{id} | GET |

#### 请求参数

| 参数名      | 类型     | 说明       |
| -------- | ------ | -------- |
| id | long | 任务ID（路径参数）      |

#### 返回参数

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| id | long | 任务ID |
| userId | long | 用户ID |
| title | string | 任务标题 |
| startTime | string | 开始时间 |
| endTime | string | 结束时间 |
| description | string | 任务描述 |
| status | string | 状态 |
| createTime | string | 创建时间 |
| updateTime | string | 更新时间 |

#### 示例

**请求示例：**
```
GET /api/fixed-task/1
```

**响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": {
    "id": 1,
    "userId": 21,
    "title": "晨间锻炼",
    "startTime": "2024-01-15T07:00:00.000Z",
    "endTime": "2024-01-15T08:00:00.000Z",
    "description": "每日晨跑，保持身体健康",
    "status": "pending",
    "createTime": "2024-01-15T06:00:00.000Z",
    "updateTime": "2024-01-15T06:00:00.000Z"
  }
}
```

### 查询我的固定任务

| 地址     | 请求方法 |
| ------ | ---- |
| /api/fixed-task/my | GET |

#### 请求参数

无需参数（通过JWT令牌获取用户ID）

#### 返回参数

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| data | array | 固定任务列表 |

数组中每个元素包含以下字段：

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| id | long | 任务ID |
| userId | long | 用户ID |
| title | string | 任务标题 |
| startTime | string | 开始时间 |
| endTime | string | 结束时间 |
| description | string | 任务描述 |
| status | string | 状态 |
| createTime | string | 创建时间 |
| updateTime | string | 更新时间 |

#### 示例

**请求示例：**
```
GET /api/fixed-task/my
```

**响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": [
    {
      "id": 1,
      "userId": 21,
      "title": "晨间锻炼",
      "startTime": "2024-01-15T07:00:00.000Z",
      "endTime": "2024-01-15T08:00:00.000Z",
      "description": "每日晨跑，保持身体健康",
      "status": "pending",
      "createTime": "2024-01-15T06:00:00.000Z",
      "updateTime": "2024-01-15T06:00:00.000Z"
    },
    {
      "id": 2,
      "userId": 21,
      "title": "工作会议",
      "startTime": "2024-01-15T09:00:00.000Z",
      "endTime": "2024-01-15T10:00:00.000Z",
      "description": "每周一的团队例会",
      "status": "pending",
      "createTime": "2024-01-15T08:00:00.000Z",
      "updateTime": "2024-01-15T08:00:00.000Z"
    }
  ]
}
```

### 按时间范围查询固定任务

| 地址     | 请求方法 |
| ------ | ---- |
| /api/fixed-task/range | GET |

#### 请求参数

| 参数名      | 类型     | 说明       |
| -------- | ------ | -------- |
| startTime | string | 开始时间（必填，格式：yyyy-MM-dd'T'HH:mm:ss）      |
| endTime | string | 结束时间（必填，格式：yyyy-MM-dd'T'HH:mm:ss）       |

#### 返回参数

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| data | array | 固定任务列表 |

数组中每个元素包含以下字段：

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| id | long | 任务ID |
| userId | long | 用户ID |
| title | string | 任务标题 |
| startTime | string | 开始时间 |
| endTime | string | 结束时间 |
| description | string | 任务描述 |
| status | string | 状态 |
| createTime | string | 创建时间 |
| updateTime | string | 更新时间 |

#### 示例

**请求示例：**
```
GET /api/fixed-task/range?startTime=2024-01-15T00:00:00&endTime=2024-01-15T23:59:59
```

**响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": [
    {
      "id": 1,
      "userId": 21,
      "title": "晨间锻炼",
      "startTime": "2024-01-15T07:00:00.000Z",
      "endTime": "2024-01-15T08:00:00.000Z",
      "description": "每日晨跑，保持身体健康",
      "status": "pending",
      "createTime": "2024-01-15T06:00:00.000Z",
      "updateTime": "2024-01-15T06:00:00.000Z"
    }
  ]
}
```

**错误响应示例：**
```json
{
  "code": 1,
  "message": "该时间段已有其他固定任务，请选择其他时间",
  "data": null
}
```

## 今日目标管理接口

### 创建今日目标

| 地址                | 请求方法 |
| ----------------- | ---- |
| /api/today-goal   | POST |

#### 请求参数

| 参数名  | 类型     | 说明   |
| ---- | ------ | ---- |
| goal | string | 目标内容 |

#### 返回参数

| 参数名        | 类型       | 说明   |
| ---------- | -------- | ---- |
| id         | long     | 目标ID |
| userId     | long     | 用户ID |
| goal       | string   | 目标内容 |
| createTime | datetime | 创建时间 |
| updateTime | datetime | 更新时间 |

#### 示例

**请求示例：**
```json
{
  "goal": "完成项目文档编写"
}
```

**成功响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": {
    "id": 1,
    "userId": 1,
    "goal": "完成项目文档编写",
    "createTime": "2024-01-20 10:30:00",
    "updateTime": "2024-01-20 10:30:00"
  }
}
```

### 更新今日目标

| 地址                    | 请求方法 |
| --------------------- | ---- |
| /api/today-goal/{id}  | PUT  |

#### 请求参数

| 参数名  | 类型     | 说明   |
| ---- | ------ | ---- |
| id   | long   | 目标ID |
| goal | string | 目标内容 |

#### 返回参数

| 参数名        | 类型       | 说明   |
| ---------- | -------- | ---- |
| id         | long     | 目标ID |
| userId     | long     | 用户ID |
| goal       | string   | 目标内容 |
| createTime | datetime | 创建时间 |
| updateTime | datetime | 更新时间 |

#### 示例

**请求示例：**
```json
{
  "goal": "完成项目文档编写和代码review"
}
```

**成功响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": {
    "id": 1,
    "userId": 1,
    "goal": "完成项目文档编写和代码review",
    "createTime": "2024-01-20 10:30:00",
    "updateTime": "2024-01-20 11:15:00"
  }
}
```

### 查询今日目标

| 地址                    | 请求方法 |
| --------------------- | ---- |
| /api/today-goal/{id}  | GET  |

#### 请求参数

| 参数名 | 类型   | 说明   |
| --- | ---- | ---- |
| id  | long | 目标ID |

#### 返回参数

| 参数名        | 类型       | 说明   |
| ---------- | -------- | ---- |
| id         | long     | 目标ID |
| userId     | long     | 用户ID |
| goal       | string   | 目标内容 |
| createTime | datetime | 创建时间 |
| updateTime | datetime | 更新时间 |

#### 示例

**成功响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": {
    "id": 1,
    "userId": 1,
    "goal": "完成项目文档编写和代码review",
    "createTime": "2024-01-20 10:30:00",
    "updateTime": "2024-01-20 11:15:00"
  }
}
```

### 查询我的今日目标

| 地址                  | 请求方法 |
| ------------------- | ---- |
| /api/today-goal/my  | GET  |

#### 请求参数

无

#### 返回参数

| 参数名        | 类型       | 说明     |
| ---------- | -------- | ------ |
| id         | long     | 目标ID   |
| userId     | long     | 用户ID   |
| goal       | string   | 目标内容   |
| createTime | datetime | 创建时间   |
| updateTime | datetime | 更新时间   |

#### 示例

**成功响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": [
    {
      "id": 1,
      "userId": 1,
      "goal": "完成项目文档编写和代码review",
      "createTime": "2024-01-20 10:30:00",
      "updateTime": "2024-01-20 11:15:00"
    },
    {
      "id": 2,
      "userId": 1,
      "goal": "学习新技术栈",
      "createTime": "2024-01-20 09:00:00",
      "updateTime": "2024-01-20 09:00:00"
    }
  ]
}
```

### 删除今日目标

| 地址                    | 请求方法   |
| --------------------- | ------ |
| /api/today-goal/{id}  | DELETE |

#### 请求参数

| 参数名 | 类型   | 说明   |
| --- | ---- | ---- |
| id  | long | 目标ID |

#### 返回参数

| 参数名  | 类型   | 说明   |
| ---- | ---- | ---- |
| data | null | 无返回数据 |

#### 示例

**成功响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": null
}
```

### 删除我的所有今日目标

| 地址                      | 请求方法   |
| ----------------------- | ------ |
| /api/today-goal/my/all  | DELETE |

#### 请求参数

无

#### 返回参数

| 参数名  | 类型   | 说明   |
| ---- | ---- | ---- |
| data | null | 无返回数据 |

#### 示例

**成功响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": null
}
```

**错误响应示例：**
```json
{
  "code": 1,
  "message": "今日目标不存在或无权限访问",
  "data": null
}
```

## AI智能设计模块

### AI任务推荐

| 地址     | 请求方法 |
| ------ | ---- |
| /api/bitgain-design/recommend-tasks | POST |

#### 请求参数

无需参数（通过JWT令牌获取用户ID，自动分析用户信息和今日日程）

#### 返回参数

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| data     | array  | 推荐任务详情列表 |

每个任务详情包含以下字段：

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| id       | long   | 任务ID   |
| title    | string | 任务标题  |
| description | string | 任务描述 |
| startTime | string | 开始时间（ISO格式） |
| endTime  | string | 结束时间（ISO格式） |

#### 示例

**请求示例：**
```json
POST /api/bitgain-design/recommend-tasks
Content-Type: application/json
Authorization: Bearer {token}

{}
```

**响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": [
    {
      "id": 101,
      "title": "学习Spring Boot新特性",
      "description": "深入了解Spring Boot 3.x的新功能和改进",
      "startTime": "2024-01-20T14:00:00.000Z",
      "endTime": "2024-01-20T15:30:00.000Z"
    },
    {
      "id": 102,
      "title": "阅读技术文档",
      "description": "阅读最新的Java开发最佳实践文档",
      "startTime": "2024-01-20T16:00:00.000Z",
      "endTime": "2024-01-20T17:00:00.000Z"
    }
  ]
}
```

**错误响应示例：**
```json
{
  "code": 1,
  "message": "用户未登录",
  "data": null
}
```

### 确认推荐任务

| 地址     | 请求方法 |
| ------ | ---- |
| /api/bitgain-design/confirm-tasks | POST |

#### 请求参数

| 参数名      | 类型     | 说明       |
| -------- | ------ | -------- |
| taskActions | array | 任务操作列表（必填） |

每个任务操作包含以下字段：

| 参数名      | 类型     | 说明       |
| -------- | ------ | -------- |
| taskId   | long   | 任务ID（必填） |
| action   | string | 操作类型："commit"（确认启用）或"reject"（拒绝删除）（必填） |

#### 返回参数

| 参数名      | 类型     | 说明     |
| -------- | ------ | ------ |
| data     | string | 操作结果信息 |

#### 示例

**请求示例：**
```json
{
  "taskActions": [
    {
      "taskId": 101,
      "action": "commit"
    },
    {
      "taskId": 102,
      "action": "reject"
    },
    {
      "taskId": 103,
      "action": "commit"
    }
  ]
}
```

**响应示例：**
```json
{
  "code": 0,
  "message": null,
  "data": "成功启用2个任务，成功删除1个任务"
}
```

**错误响应示例：**
```json
{
  "code": 1,
  "message": "请选择要操作的任务",
  "data": null
}
```


