package com.itheima.model.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 客户新增/修改请求参数（前端新增与修改使用同一套表单字段）
 */
@Data
public class CustomerReq {

    /**
     * 修改时必填
     */
    private Integer id;

    private String name;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    @NotNull(message = "渠道不能为空")
    private Integer channel;
    private Integer gender;
    private Integer age;
    private String wechat;
    private String qq;
    private Integer degree;
    private Integer jobStatus;
    private Integer subject;
    private Integer courseId;

    /**
     * 来源商机id
     */
    private Integer businessId;

}
