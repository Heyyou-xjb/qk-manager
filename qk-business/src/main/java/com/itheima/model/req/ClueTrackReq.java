package com.itheima.model.req;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClueTrackReq {
    private Integer id;
    private String phone;
    private Integer channel;
    private Integer activityId;
    private String name;
    private Integer gender;
    private Integer age;
    private String wechat;
    private String qq;
    private Integer userId;
    private Integer status;
    private Integer subject;
    private Integer level;
    private LocalDateTime nextTime;
    private String record;
}
