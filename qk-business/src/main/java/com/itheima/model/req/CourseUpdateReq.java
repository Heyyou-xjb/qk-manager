package com.itheima.model.req;

import lombok.Data;

@Data
public class CourseUpdateReq {
    private Integer id;

    private Integer subject;

    private String name;

    private Integer price;

    private Integer target;

    private String description;
}
