package com.itheima.model.req;

import com.itheima.dto.PageQuery;
import lombok.Data;

@Data
public class QueryStringReq extends PageQuery {
    private String name;
    private String subject;
    private Integer target;
}
