package com.itheima.model.req;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ActivityAddReq{

    private Integer channel;
    private String name;
    private String description;
    private Integer type;
    private Double discount;
    private Integer voucher;
    private String startTime;
    private String endTime;
}
