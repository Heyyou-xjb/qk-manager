package com.itheima.model.req;

import com.itheima.dto.PageQuery;
import lombok.Data;

/**
 * 操作日志分页查询条件
 */
@Data
public class OperateLogPageReq extends PageQuery {

    /**
     * 操作人姓名
     */
    private String operateUserName;

}
