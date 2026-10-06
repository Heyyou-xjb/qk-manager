package com.itheima.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("course")
public class CourseDo {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private Integer subject;
    private String name;
    private Integer price;
    private Integer target;
    private String description;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
