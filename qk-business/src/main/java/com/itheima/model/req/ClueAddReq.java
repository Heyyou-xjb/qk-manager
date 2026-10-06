package com.itheima.model.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClueAddReq {

    @NotBlank(message = "手机号不能为空")
    private String phone;
    @NotNull(message = "渠道不能为空")
    private Integer channel;
    private Integer activityId;
    private String name;
    private Integer gender;
    private Integer age;

    @Size(min = 1, max = 50, message = "微信号不能超过20位")
    private String wechat;

    @Size(min = 1, max = 20, message = "qq号不能超过20位")
    private String qq;
}
