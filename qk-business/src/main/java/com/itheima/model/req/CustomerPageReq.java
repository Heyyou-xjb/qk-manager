package com.itheima.model.req;

import com.itheima.dto.PageQuery;
import lombok.Data;

/**
 * 客户分页查询条件
 */
@Data
public class CustomerPageReq extends PageQuery {

    private String phone;

    private String name;

    private Integer channel;

    private Integer subject;

}
