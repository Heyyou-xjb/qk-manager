package com.itheima.model.resp;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClueTrackRecordResp {
    private Integer id;
    private Integer clueId;
    private Integer userId;
    private Integer subject;
    private Integer level;
    private String record;
    private String nextTime;
    private Integer type;
    private String falseReason;
    private LocalDateTime createTime;
    private String assignName;
}