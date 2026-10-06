package com.itheima.model.resp;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商机跟进记录（含跟进人姓名）
 */
@Data
public class BusinessTrackRecordResp {

    private Integer id;
    private Integer businessId;
    private Integer userId;
    private Integer trackStatus;
    private String keyItems;
    private LocalDateTime nextTime;
    private String record;
    private LocalDateTime createTime;
    private String assignName;

}
