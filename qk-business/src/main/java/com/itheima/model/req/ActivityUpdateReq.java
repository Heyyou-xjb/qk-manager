package com.itheima.model.req;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActivityUpdateReq {

    private Integer id;
    private Integer channel;
    private String name;
    private String startTime;
    private String endTime;
    private String description;
    private Integer type;
    private Double discount;
    private Integer voucher;
    /*private LocalDateTime createTime;
    private LocalDateTime updateTime;*/
}
