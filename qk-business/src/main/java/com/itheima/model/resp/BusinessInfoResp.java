package com.itheima.model.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 商机详情（商机信息 + 跟进记录）
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessInfoResp extends BusinessPageResp {

    private List<BusinessTrackRecordResp> trackRecords;

}
