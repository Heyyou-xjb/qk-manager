package com.itheima.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商机表
 * </p>
 *
 * @author niubi
 * @since 2026-09-29
 */
@Getter
@Setter
@ToString
@TableName("business")
public class BusinessDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 线索id, 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

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
     * qq号
     */
    private String qq;

    /**
     * 意向学科，1:ai智能应用开发(java), 2:ai大模型开发(python)，3:ai鸿蒙开发，4:ai大数据，5:ai嵌入式，6:ai测试，7:ai运维
     */
    private Integer subject;

    /**
     * 意向课程, 课程id
     */
    private Integer courseId;

    /**
     * 学历, 1:高中、2:中专、3:大专、4:本科、5:硕士、6:博士、7:其他
     */
    private Integer degree;

    /**
     * 在职情况, 1: 在职, 0: 离职
     */
    private Integer jobStatus;

    /**
     * 渠道来源，1:线上活动, 2:推广介绍
     */
    private Integer channel;

    /**
     * 备注
     */
    private String remark;

    /**
     * 商机状态，1:待分配, 2:待跟进, 3:跟进中, 4:回收, 5:转客户
     */
    private Integer status;

    /**
     * 归属人id，关联用户id
     */
    private Integer userId;

    /**
     * 归属线索id
     */
    private Integer clueId;

    /**
     * 下次跟进时间
     */
    private LocalDateTime nextTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
