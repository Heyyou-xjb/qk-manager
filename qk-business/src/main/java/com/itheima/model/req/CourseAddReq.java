package com.itheima.model.req;

import lombok.Data;

@Data
public class CourseAddReq {
    private String name;
    private Integer subject;
    private Integer target;
    private String description;
    private Integer price;
}
