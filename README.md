# 轻客管家（qk-manager）项目说明文档

> 文档生成时间：2026-10-05 ｜ 适用代码版本：v1.0-SNAPSHOT（当前工作区）

## 一、项目简介

轻客管家是一套「线索 → 商机 → 客户」的销售管理系统，后端为 Spring Boot 3 + MyBatis-Plus 的多模块 Maven 工程，
前端为打包好的 Vue3 + Element Plus 静态资源（由 nginx 托管并反向代理后端接口）。

核心业务链路：

```
线索池(待分配) → 分配专员 → 跟进 → 转为商机 → 分配专员 → 跟进 → 转客户
                                  ↘ 伪线索(回收)
```

## 二、技术栈与运行环境

| 分类 | 选型 |
| --- | --- |
| JDK | 21（`java.version=21`，与 `.idea` 中配置的 JDK21 一致） |
| 框架 | Spring Boot 3.4.8、Spring MVC、Spring AOP |
| 持久层 | MyBatis-Plus 3.5.17（`com.baomidou.mybatisplus.spring.*` 包名）、MySQL 8.0 |
| 工具库 | Lombok 1.18.46、Hutool 5.8.42、JJWT 0.9.1、Aliyun OSS SDK 3.18.4 |
| 数据库 | MySQL，库名 `qk`（默认 `jdbc:mysql://localhost:3306/qk`） |
| 前端 | Vue3 + Element Plus 构建产物，nginx 1.24 监听 **90** 端口，`/api/*` 反代到 `localhost:8080` |

### 目录结构

```
qk-manager
├── pom.xml                     # 父工程（统一版本、Lombok 注解处理器、编译级别 21）
├── .env.example                # 环境变量模板（复制为 .env 使用；.env 已被 .gitignore 忽略）
├── frontend/                   # 前端运行产物：index.html + assets/ + nginx.conf + 说明
├── scripts/                    # 启动脚本：start-backend.ps1 / start-frontend.ps1
├── sql/                        # 数据库脚本：schema.sql（全库结构）/ customer.sql（客户表）
├── qk-framework                # 框架层：通用返回、分页、异常、JWT、拦截器、OSS、枚举、MP 配置
│   └── src/main/java/com/itheima
│       ├── dto/                # Result、PageResult、PageQuery
│       ├── exception/          # BusinessException、GlobalExceptionHandler
│       ├── interceptor/        # LoginInterceptor（token 校验 + UserHolder）
│       ├── mybatisplus/        # MyMetaObjectHandler（create_time/update_time 自动填充）
│       ├── properties/ utils/  # OssProperties、OssTemplate、JwtUtil、UserHolder
│       └── enums/              # ClueStatueEnum（线索状态枚举）
├── qk-business                 # 业务层：controller / service / mapper / model / aop
│   └── src/main/java/com/itheima
│       ├── Controller/         # 12 个控制器（见接口清单）
│       ├── service(impl)/      # 业务逻辑
│       ├── mapper/             # MyBatis-Plus Mapper
│       ├── model/
│       │   ├── dataobject/     # 数据库实体（XxxDO）
│       │   ├── req/            # 请求参数（XxxAddReq/XxxUpdateReq/XxxPageReq…）
│       │   └── resp/           # 响应对象（XxxResp）
│       └── aop/                # @Log 注解 + 操作日志切面、耗时统计切面
└── qk-start                    # 启动模块：Application、application.yml、logback.xml
```

### 启动方式

**1）准备环境变量**：复制 `.env.example` 为 `.env` 并填写本机配置（`.env` 已在 `.gitignore` 中，切勿提交）；
或在 IDEA 的 Run Configuration → Environment variables 中配置同一组变量。

| 变量 | 说明 |
| --- | --- |
| `QK_DB_URL` / `QK_DB_USERNAME` / `QK_DB_PASSWORD` | MySQL 连接（默认 `jdbc:mysql://localhost:3306/qk`） |
| `QK_OSS_REGION` / `QK_OSS_ENDPOINT` / `QK_OSS_BUCKET` / `QK_OSS_ACCESS_KEY_ID` / `QK_OSS_ACCESS_KEY_SECRET` | 阿里云 OSS 图片上传（不使用上传功能可留空） |
| `QK_JWT_SECRET_KEY` / `QK_JWT_EXPIRATION_TIME` | JWT 签名密钥与有效期（生产环境务必替换默认值） |
| `QK_SERVER_PORT` | 后端端口，默认 8080 |

**2）初始化数据库**（新环境）：先执行 `sql/schema.sql` 建库表，如库中还没有客户表再执行 `sql/customer.sql`。

**3）打包并启动后端**：

```powershell
mvn -DskipTests package                    # 打包前先停掉正在运行的后端（Windows 会锁定 jar）
pwsh ./scripts/start-backend.ps1           # 读取 .env 并启动；等价于 java -jar qk-start/target/qk-start-1.0-SNAPSHOT.jar
```

**4）启动前端**：

```powershell
$env:QK_NGINX_DIR = 'D:\qk-manager-nginx\nginx-1.24.0-qk'   # 改成你的 nginx 目录
pwsh ./scripts/start-frontend.ps1                            # 同步 frontend/ 到 nginx 站点目录并启动 nginx
```

**5）访问**：<http://localhost:90>（前端页面）、<http://localhost:8080>（后端接口直连）。

> 账号说明：账号数据保存在数据库 `user` 表中（不随仓库分发）。通过「用户管理」新增的用户，默认密码为 `用户名+123`；
> 登录成功后返回 `token`，后续请求放在请求头 `token` 中。

## 三、数据库设计

库名 `qk`，字符集 `utf8mb4 / utf8mb4_0900_ai_ci`。现有表：

| 表名 | 说明 |
| --- | --- |
| `user` | 用户表（关联 `dept`、`role`） |
| `role` | 角色表（`label` 决定权限：admin / clue_operator / business_operator / common_user） |
| `dept` | 部门表 |
| `course` | 课程表（意向课程） |
| `activity` | 营销活动表（线索、商机的渠道来源） |
| `clue` | 线索表（1待分配 2待跟进 3跟进中 4伪线索 5转商机） |
| `clue_track_record` | 线索跟进记录（type 1正常跟进 0伪线索） |
| `business` | 商机表（1待分配 2待跟进 3跟进中 4回收 5转客户；`clue_id` 记录来源线索） |
| `business_track_record` | 商机跟进记录（track_status 1接通 2拒绝 3无人接听） |
| `customer` | **客户表（本项目新增，DDL 见 `sql/customer.sql`）** |
| `operate_log` | 操作日志（`@Log` 注解切面写入，`operate_user_id` 关联 user） |

客户表结构（`sql/customer.sql`）：

```sql
CREATE TABLE IF NOT EXISTS `customer` (
    `id`          int unsigned     NOT NULL AUTO_INCREMENT COMMENT '客户id, 主键',
    `name`        varchar(20)               DEFAULT NULL COMMENT '客户姓名',
    `phone`       char(11)         NOT NULL COMMENT '手机号',
    `channel`     tinyint unsigned NOT NULL COMMENT '渠道来源，1:线上活动, 2:推广介绍',
    `gender`      tinyint unsigned          DEFAULT NULL COMMENT '性别，1:男, 2:女',
    `age`         tinyint unsigned          DEFAULT NULL COMMENT '年龄',
    `wechat`      varchar(30)               DEFAULT NULL COMMENT '微信号',
    `qq`          varchar(15)               DEFAULT NULL COMMENT 'qq号',
    `degree`      tinyint unsigned          DEFAULT NULL COMMENT '学历, 1:高中、2:中专、3:大专、4:本科、5:硕士、6:博士、7:其他',
    `job_status`  tinyint unsigned          DEFAULT NULL COMMENT '在职情况, 1:在职, 0:离职',
    `subject`     tinyint unsigned          DEFAULT NULL COMMENT '意向学科',
    `course_id`   int unsigned              DEFAULT NULL COMMENT '意向课程, 课程id',
    `business_id` int unsigned              DEFAULT NULL COMMENT '来源商机id',
    `create_time` datetime         NOT NULL COMMENT '创建时间',
    `update_time` datetime         NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户表';
```

## 四、通用约定

| 约定 | 说明 |
| --- | --- |
| 统一返回 | `Result<T>{code:0/1, msg, data}`，`code=1` 成功；前端 axios 拦截器只认 `code===1` |
| 分页返回 | `PageResult<T>{total, rows}`，接收 `page/pageSize`（`PageQuery`，空值自动兜底为 1/10） |
| 分页查询 | Mapper 返回 `Page<T>`，Service 用 `PageResult.of(page.getRecords(), page.getTotal())` 包装 |
| 异常处理 | `BusinessException` → 返回业务提示；参数校验 `@Valid` → 拼接字段提示；`DuplicateKeyException` → “数据已存在…”；其它异常 → “服务器错误” |
| 鉴权 | `LoginInterceptor` 拦截 `/**`（放行 `/login`），校验请求头 `token`（JWT），失败返回 401；解析出的用户 id 放入 `UserHolder`，请求结束清理 |
| 自动填充 | `MyMetaObjectHandler` 为标注 `@TableField(fill=INSERT/INSERT_UPDATE)` 的 `createTime/updateTime` 赋值 |
| 数据权限 | 线索/商机列表：`role.label=admin` 看全部，其它角色只看归属自己的记录（`user_id = 当前登录人`） |
| 操作日志 | 方法上加 `@Log`，`LogReportAspect` 记录类名/方法名/参数/返回值/耗时到 `operate_log` |
| 文件上传 | `POST /upload` 上传到阿里云 OSS，返回可访问 URL（UUID 重命名，存放于 `hahaha/` 目录） |

## 五、接口清单（13 个控制器 / 全部已实现）

### 1. 登录与概览

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/login` | 登录，返回 `{id,username,name,image,roleLabel,token}` |
| GET | `/report/overview` | 首页数据概览（线索 6 项 + 商机 6 项统计） |

### 2. 资源管理

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/courses` | 课程分页（name 模糊、target、subject） |
| GET | `/courses/{id}` / `/courses/list` / `/courses/subject/{subject}` | 详情 / 全部 / 按学科查 |
| POST / PUT / DELETE | `/courses`、`/courses`、`/courses/{id}` | 新增 / 修改 / 删除 |
| GET | `/activities` / `/activities/{id}` / `/activities/type/{type}` | 活动分页（channel、type）/ 详情 / 按类型 |
| POST / PUT / DELETE | `/activities`、`/activities`、`/activities/{id}` | 新增 / 修改 / 删除 |
| GET | `/depts` / `/depts/{id}` / `/depts/list` | 部门分页（name、status）/ 详情 / 全部 |
| POST / PUT / DELETE | `/depts`、`/depts`、`/depts/{id}` | 新增（名称唯一校验）/ 修改 / 删除 |
| GET | `/roles` / `/roles/{id}` / `/roles/list` | 角色分页（name、label）/ 详情 / 全部 |
| POST / PUT / DELETE | `/roles`、`/roles`、`/roles/{id}` | 新增（名称、标签唯一校验）/ 修改 / 删除 |
| GET | `/users` | 用户分页（name、phone、deptId、status），联表返回 `deptName/roleName` |
| GET | `/users/{id}` / `/users/list` / `/users/role/{roleLabel}` / `/users/dept/{deptId}` | 详情 / 全部 / 按角色 / 按部门 |
| POST / PUT / DELETE | `/users`、`/users`、`/users/{ids}` | 新增（默认密码=用户名+123）/ 修改 / 批量删除 |
| POST | `/upload` | 图片上传（字段名 `image`） |

### 3. 线索管理（`ClueController`）

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/clues` | 线索分页：`clueId/phone/status/channel/assignName`，固定 `status in (1,2,3)`，管理员看全部、专员看自己 |
| GET | `/clues/{id}` | 线索详情，含 `assignName`（归属人）、`activityName`（活动）、`trackRecords`（跟进历史） |
| GET | `/clues/pool` | 线索池分页：`clueId/phone/channel` + 分页 |
| POST | `/clues` | 新增线索（手机号唯一，默认状态 1 待分配） |
| PUT | `/clues` | 跟进线索（`id/subject/level/nextTime/record`），状态改为 3 并写入跟进记录 |
| PUT | `/clues/assign/{clueId}/{userId}` | 分配线索：`user_id=userId`、状态改为 2 |
| PUT | `/clues/false/{id}` | 标记伪线索（`reason/remark`）：状态改为 4，写入 type=0 的跟进记录 |
| PUT | `/clues/toBusiness/{id}` | 转商机：要求当前状态=3，线索状态改为 5，并**自动复制生成一条待分配商机**（`clue_id` 记录来源） |

### 4. 商机管理（`BusinessController`）

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/businesses` | 商机分页：`businessId/name/phone/status/assignName`，固定 `status in (1,2,3)`，管理员看全部、专员看自己 |
| GET | `/businesses/{id}` | 商机详情，含 `assignName` 与 `trackRecords` |
| GET | `/businesses/pool` | 商机公海分页：`businessId/name/phone/subject`，只查 `status=1`（待分配） |
| POST | `/businesses` | 新增商机（手机号唯一、渠道必填，默认状态 1 进入公海） |
| PUT | `/businesses` | 跟进商机：更新商机（状态=3、`next_time`）并写入 `business_track_record`（`trackStatus/keyItems[]/nextTime/record`） |
| PUT | `/businesses/assign/{businessId}/{userId}` | 分配商机：`user_id=userId`、状态改为 2 |
| PUT | `/businesses/back/{id}` | 退回公海：清空归属人与下次跟进时间、状态回到 1 |
| POST | `/businesses/toCustomer/{id}` | 转客户：复制商机信息到 `customer` 表（记录 `business_id`）、商机状态改为 5；重复转同一商机会被拦截 |

### 5. 客户管理（`CustomerController`）

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/customers` | 客户分页：`phone/name/channel/subject`，联表返回 `courseName`（意向课程名） |
| GET | `/customers/{id}` | 客户详情（含 `courseName`） |
| POST | `/customers` | 新增客户（手机号唯一、渠道必填） |
| PUT | `/customers` | 修改客户（`id` 必填） |

### 6. 操作日志（`OperateLogController`）

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/logs` | 操作日志分页：`operateUserName`（操作人姓名模糊），联表返回 `operateUserName`，按 id 倒序 |

## 六、权限模型

后端只有「登录校验」（`LoginInterceptor`），没有接口级鉴权；细粒度权限由前端按 `roleLabel` 控制按钮，
后端在列表查询上做「数据权限」。

| 前端位置 | 按钮 | 条件 |
| --- | --- | --- |
| 线索列表 / 商机列表 | 分配 | `roleLabel === "admin"` 且记录状态为 1 |
| 线索列表 | 跟进 / 伪线索 / 转商机 | `roleLabel === "clue_operator"` 且为记录归属人 |
| 商机列表 | 跟进 / 退回公海 / 转客户 | `roleLabel === "business_operator"` 且为记录归属人 |

数据权限（后端）：`ClueServiceImpl.queryPageList`、`BusinessServiceImpl.getBusinessList` 中，
管理员（`role.label = admin`）不限制归属人，其它角色强制 `user_id = 当前登录用户`。

## 七、本次变更记录（2026-10-05）

### 新增接口（补齐前端调用的全部剩余接口）

| 模块 | 新增内容 |
| --- | --- |
| 商机 | `GET /businesses/pool`（公海）、`GET /businesses/{id}`（详情+跟进历史）、`PUT /businesses`（跟进+记录）、`PUT /businesses/assign/{id}/{userId}`（分配）、`PUT /businesses/back/{id}`（退回公海）、`POST /businesses/toCustomer/{id}`（转客户） |
| 客户 | 全新模块：`CustomerDO/CustomerMapper(.xml)/CustomerService(Impl)/CustomerController` + `GET /customers`、`GET /customers/{id}`、`POST /customers`、`PUT /customers`；新增 `customer` 表（`sql/customer.sql`） |
| 日志 | `GET /logs`：`OperateLogPageReq/OperateLogResp/OperateLogMapper.queryPageList/OperateLogService.queryPageList` |
| 线索 | `GET /clues/{id}` 补全 `assignName`、`activityName`（新增 `ClueMapper.queryClueById`） |

### 修复

| 问题 | 处理 |
| --- | --- |
| `BusinessMapper.getBusinessList` 返回类型写成 `PageResult`、SQL 无任何条件与排序，商机列表分页/筛选失效 | 改为 `Page<BusinessPageResp>` + 动态条件 + 关联 `user` 取 `assignName` + 归属人数据权限 + `order by b.id desc` |
| `BusinessAddReq` 中 `age/subject/courseId` 为 String，`BeanUtils` 复制到实体时类型不匹配会丢字段；`channel` 缺失时报“Field 'channel' doesn't have a default value” | 字段类型改为 Integer，`channel` 增加 `@NotNull` 校验；新增商机显式设置 `status=1` 进入公海 |
| 商机/客户等新增接口缺少 `@Valid` | `BusinessController.addBusiness`、`CustomerController.addCustomer/updateCustomer` 增加 `@Valid` |
| `OperateLogController` 类上有 `/operateLogDO` 前缀，日志接口无法按前端路径访问 | 去掉类级 `@RequestMapping`，新增 `GET /logs` |

## 八、测试与验证记录（2026-10-05）

环境：JDK21 运行后端 8080、nginx 90；数据库 `qk`；测试数据已全部清理（测试前后基线一致）。

| 验证项 | 结果 |
| --- | --- |
| `mvn -DskipTests package` | BUILD SUCCESS（release 21，qk-business 105 个源文件） |
| 只读接口（27 个 URL） | 全部 `code=1` |
| 资源管理写操作（部门/课程/活动/角色/用户的增改删 + 上传 + 新用户默认密码登录） | 17 项全部通过 |
| 商机闭环：新增→公海查询→分配→跟进（含跟进记录/沟通重点/下次跟进时间）→退回公海→转客户→重复转客户拦截 | 全部通过（含数据库字段核对） |
| 客户闭环：转客户生成、手工新增、详情、修改、条件查询、重复手机号拦截 | 全部通过 |
| 线索闭环：新增→分配（状态2+归属人）→详情（归属人/活动名/跟进历史）→跟进（状态3）→转商机（状态5，自动生成商机）；伪线索（状态4+记录） | 全部通过 |
| 前端浏览器 E2E（Playwright + 系统 Chrome，无头） | 登录成功进入 `/home`；遍历 12 个页面：**console 报错 0、HTTP≥400 请求 0** |
| 数据清理 | 线索 16→13、商机 6→2、客户 2→0、跟进记录均回到基线，无残留测试数据 |

## 九、已知问题与建议

1. **线索池过滤条件**：`ClueMapper.xml` 的 `queryPoolList` 目前过滤 `c.status = 4`（伪线索，2026-09-30 的改动）。
   按“线索池=待分配”的语义应为 `c.status = 1`；如确认为“伪线索回收池”，建议把该语义写入需求说明。
2. **接口级鉴权缺失**：后端仅校验 token，任何登录用户都能直接调用全部接口（含删除）；如需严格权限，
   建议在拦截器或 AOP 中按 `roleLabel` 做接口鉴权。
3. **敏感信息（已处理）**：数据库口令、阿里云 AK/SK、JWT 密钥已从 `application.yml` 移到环境变量（模板见 `.env.example`），
   代码生成脚本中的硬编码口令同样改为读取环境变量。⚠️ 这些凭据曾出现在本地提交中，**建议重置数据库口令与 OSS AK/SK**，
   仓库保持私有或只保留示例值。仍未处理：`UserResp` 会把密码哈希返回给前端（`/users` 可见 `password` 字段）。
4. **密码策略**：没有“修改密码”接口，新增用户密码固定为 `用户名+123`，安全性较低。
5. **字段长度**：`business_track_record.key_items` 为 `varchar(50)`、`record` 为 `varchar(50)`，
   沟通重点多选拼接过长时会插入失败，建议放宽字段长度或在前端限制。
6. **线索列表固定 `status in (1,2,3)`**：伪线索(4)、转商机(5) 不会出现在线索列表，只能通过线索池/其它页面查看。
7. **测试类**：`OssTest` 依赖本地 `E:\download.jpg` 并会真实上传 OSS（已加“文件不存在则跳过”）；
   `MpGenTest` 为代码生成脚手架（连接信息与输出目录改为读取环境变量），不属于业务代码。
