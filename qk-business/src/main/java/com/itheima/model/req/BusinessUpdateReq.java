package com.itheima.model.req;

import lombok.Data;

import java.util.List;

/**
 * 商机跟进（修改）请求参数：商机信息 + 本次跟进信息
 */
@Data
public class BusinessUpdateReq {

    private Integer id;
    private String name;
    private String phone;
    private Integer gender;
    private Integer age;
    private String wechat;
    private String qq;
    private Integer subject;
    private Integer courseId;
    private Integer degree;
    private Integer jobStatus;
    private Integer channel;
    private String remark;

    /**
     * 跟进状态, 1:接通, 2:拒绝, 3:无人接听
     */
    private Integer trackStatus;

    /**
     * 沟通重点，前端是多选数组
     */
    private List<String> keyItems;

    /**
     * 下次跟进时间，格式 yyyy-MM-dd'T'HH:mm:ss，可能为空字符串
     */
    private String nextTime;

    /**
     * 沟通纪要
     */
    private String record;

}
