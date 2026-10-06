package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.model.dataobject.CustomerDO;
import com.itheima.model.req.CustomerPageReq;
import com.itheima.model.resp.CustomerPageResp;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author niubi
 * @since 2026-10-05
 */
@Mapper
public interface CustomerMapper extends BaseMapper<CustomerDO> {

    Page<CustomerPageResp> queryPageList(Page<CustomerPageResp> page, CustomerPageReq req);

    CustomerPageResp queryCustomerById(Integer id);

}
