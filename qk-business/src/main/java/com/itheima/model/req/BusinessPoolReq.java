package com.itheima.model.req;

import com.itheima.dto.PageQuery;
import lombok.Data;

/**
 * 商机公海（待分配商机）查询条件
 */
@Data
public class BusinessPoolReq extends PageQuery {

    private String businessId;

    private String name;

    private String phone;

    private Integer subject;

}
