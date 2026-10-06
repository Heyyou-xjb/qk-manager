package com.itheima.model.req;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserUpdateReq{

    private Integer id;

    private String username;

    private String name;

    private String phone;

    private String email;

    private Integer gender;

    private Integer status;

    private Integer deptId;

    private Integer roleId;

    private String image;

    private String remark;

}
