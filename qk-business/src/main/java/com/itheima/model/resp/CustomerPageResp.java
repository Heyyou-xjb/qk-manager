package com.itheima.model.resp;

import com.itheima.model.dataobject.CustomerDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户分页查询返回结果（带意向课程名称）
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerPageResp extends CustomerDO {

    private String courseName;

}
