package com.itheima.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("activity")
public class ActivityDo implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    private Integer channel;

    private String name;

    private String description;

    private Integer type;

    private Double discount;

    private Integer voucher;

    private String startTime;

    private String endTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
