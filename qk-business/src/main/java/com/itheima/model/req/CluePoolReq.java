package com.itheima.model.req;

import com.itheima.dto.PageQuery;
import lombok.Data;

@Data
public class CluePoolReq extends PageQuery {
    private Integer clueId;

    private String phone;

    private Integer channel;
}
