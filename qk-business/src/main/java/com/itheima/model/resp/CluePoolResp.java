package com.itheima.model.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 线索池（待分配线索）分页查询返回结果。
 * 比线索列表多一个活动名称字段（线索池列表需要展示“渠道来源 - 活动信息”）
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CluePoolResp extends CluePageResp {

    /**
     * 活动名称，来自活动表
     */
    private String activityName;

}
