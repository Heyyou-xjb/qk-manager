package com.itheima.model.req;

import com.itheima.dto.PageQuery;
import lombok.Data;

@Data
public class CluePageReq extends PageQuery {

    private Integer clueId;

    private String phone;

    private Integer status;

    private Integer channel;

    private String assignName;
}
