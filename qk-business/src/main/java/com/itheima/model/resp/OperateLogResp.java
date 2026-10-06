package com.itheima.model.resp;

import com.itheima.model.dataobject.OperateLogDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志分页查询返回结果（带操作人姓名）
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OperateLogResp extends OperateLogDO {

    private String operateUserName;

}
