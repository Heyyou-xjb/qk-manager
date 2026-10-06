package com.itheima.model.resp;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CluePageResp {
    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 渠道来源，1:线上活动, 2:推广介绍
     */
    private Integer channel;

    /**
     * 活动信息，关联活动的ID
     */
    private Integer activityId;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 性别，1:男, 2:女
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 归属人ID，关联用户ID
     */
    private Integer userId;

    /**
     * 线索状态，1:待分配, 2:待跟进, 3:跟进中, 4:伪线索, 5:转为商机
     */
    private Integer status;

    /**
     * 意向学科，1:AI智能应用开发(Java), 2:AI大模型开发(Python)，3:AI鸿蒙开发，4:AI大数据，5:AI嵌入式，6:AI测试，7:AI运维
     */
    private Integer subject;

    /**
     * 意向等级, 1:近期学习、2:打算学习(考虑中)、3:进行了解、4:打酱油
     */
    private Integer level;

    /**
     * 下次跟进时间
     */
    private LocalDateTime nextTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    private String assignName;
}
