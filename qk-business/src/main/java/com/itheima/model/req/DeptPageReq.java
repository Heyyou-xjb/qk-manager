package com.itheima.model.req;

import com.itheima.dto.PageQuery;
import lombok.Data;

@Data
public class DeptPageReq extends PageQuery {

    private String name;
    private Integer status;

}