package com.itheima.model.req;

import com.itheima.dto.PageQuery;
import lombok.Data;

@Data
public class BusinessPageReq extends PageQuery {

    private String businessId;
    private String name;
    private String phone;
    private String status;
    private String assignName;

}
