package com.itheima.model.req;

import com.itheima.dto.PageQuery;
import lombok.Data;

@Data
public class UserPageReq extends PageQuery {

    private String name;
    private Integer status;
    private String phone;
    private Integer deptId;

}
