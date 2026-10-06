package com.itheima.model.req;

import com.itheima.dto.PageQuery;
import lombok.Data;

@Data
public class ActivityPageReq extends PageQuery {

    private Integer channel;

    private Integer type;

}