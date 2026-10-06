package com.itheima.model.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BusinessAddReq {

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

    @NotNull(message = "渠道不能为空")
    private Integer channel;
    private String remark;
}
