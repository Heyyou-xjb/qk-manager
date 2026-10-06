package com.itheima.service;

import com.itheima.dto.PageResult;
import com.itheima.model.dataobject.BusinessDO;
import com.baomidou.mybatisplus.spring.service.IService;
import com.itheima.model.req.BusinessAddReq;
import com.itheima.model.req.BusinessPageReq;
import com.itheima.model.req.BusinessPoolReq;
import com.itheima.model.req.BusinessUpdateReq;
import com.itheima.model.resp.BusinessInfoResp;
import com.itheima.model.resp.BusinessPageResp;

/**
 * <p>
 * 商机表 服务类
 * </p>
 *
 * @author niubi
 * @since 2026-09-29
 */
public interface BusinessService extends IService<BusinessDO> {

    PageResult<BusinessPageResp> getBusinessList(BusinessPageReq req);

    PageResult<BusinessPageResp> getBusinessPoolList(BusinessPoolReq req);

    BusinessInfoResp getBusinessById(Integer id);

    void addBusiness(BusinessAddReq req);

    void updateBusiness(BusinessUpdateReq req);

    void assignBusiness(Integer id, Integer userId);

    void backBusiness(Integer id);

    void toCustomer(Integer id);
}
