package com.itheima.model.req;

import lombok.Data;

@Data
public class UserAddReq {

    /**
     * 用户名，唯一
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号，唯一
     */
    private String phone;

    /**
     * 邮箱，唯一
     */
    private String email;

    /**
     * 性别，1: 男，2: 女
     */
    private Integer gender;

    /**
     * 状态，1: 正常，0: 停用
     */
    private Integer status;

    /**
     * 部门id，关联部门表主键
     */
    private Integer deptId;

    /**
     * 角色id，关联角色表主键
     */
    private Integer roleId;

    /**
     * 头像url
     */
    private String image;

    /**
     * 备注，50字以内
     */
    private String remark;


}
