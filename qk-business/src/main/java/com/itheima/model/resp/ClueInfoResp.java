package com.itheima.model.resp;

import com.itheima.model.dataobject.ClueDO;
import lombok.Data;

import java.util.List;

@Data
public class ClueInfoResp extends ClueDO {

    /**
     * 归属人名称
     */
    private String assignName;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 跟踪记录
     */
    private List<ClueTrackRecordResp> trackRecords;

}
