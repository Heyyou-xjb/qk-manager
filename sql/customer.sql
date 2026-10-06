-- 客户表：商机“转客户”后把商机信息复制到本表，也支持在客户管理页面手工新增/修改客户
CREATE TABLE IF NOT EXISTS `customer` (
    `id`          int unsigned   NOT NULL AUTO_INCREMENT COMMENT '客户id, 主键',
    `name`        varchar(20)             DEFAULT NULL COMMENT '客户姓名',
    `phone`       char(11)       NOT NULL COMMENT '手机号',
    `channel`     tinyint unsigned NOT NULL COMMENT '渠道来源，1:线上活动, 2:推广介绍',
    `gender`      tinyint unsigned        DEFAULT NULL COMMENT '性别，1:男, 2:女',
    `age`         tinyint unsigned        DEFAULT NULL COMMENT '年龄',
    `wechat`      varchar(30)             DEFAULT NULL COMMENT '微信号',
    `qq`          varchar(15)             DEFAULT NULL COMMENT 'qq号',
    `degree`      tinyint unsigned        DEFAULT NULL COMMENT '学历, 1:高中、2:中专、3:大专、4:本科、5:硕士、6:博士、7:其他',
    `job_status`  tinyint unsigned        DEFAULT NULL COMMENT '在职情况, 1:在职, 0:离职',
    `subject`     tinyint unsigned        DEFAULT NULL COMMENT '意向学科，1:ai智能应用开发(java), 2:ai大模型开发(python)，3:ai鸿蒙开发，4:ai大数据，5:ai嵌入式，6:ai测试，7:ai运维',
    `course_id`   int unsigned            DEFAULT NULL COMMENT '意向课程, 课程id',
    `business_id` int unsigned            DEFAULT NULL COMMENT '来源商机id',
    `create_time` datetime       NOT NULL COMMENT '创建时间',
    `update_time` datetime       NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户表';
