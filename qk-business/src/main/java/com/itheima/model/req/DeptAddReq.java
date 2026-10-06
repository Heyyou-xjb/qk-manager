package com.itheima.model.req;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeptAddReq {
    private Integer id;
    private String name;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
