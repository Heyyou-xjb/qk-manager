package com.itheima.service;

import com.itheima.dto.PageResult;
import com.itheima.model.dataobject.CustomerDO;
import com.baomidou.mybatisplus.spring.service.IService;
import com.itheima.model.req.CustomerPageReq;
import com.itheima.model.req.CustomerReq;
import com.itheima.model.resp.CustomerPageResp;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author niubi
 * @since 2026-10-05
 */
public interface CustomerService extends IService<CustomerDO> {

    PageResult<CustomerPageResp> queryPageList(CustomerPageReq req);

    void addCustomer(CustomerReq req);

    void updateCustomer(CustomerReq req);

    CustomerPageResp queryCustomerById(Integer id);

}
