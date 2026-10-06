package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.PageResult;
import com.itheima.exception.BusinessException;
import com.itheima.mapper.CustomerMapper;
import com.itheima.model.dataobject.CustomerDO;
import com.itheima.model.req.CustomerPageReq;
import com.itheima.model.req.CustomerReq;
import com.itheima.model.resp.CustomerPageResp;
import com.itheima.service.CustomerService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author niubi
 * @since 2026-10-05
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, CustomerDO> implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public PageResult<CustomerPageResp> queryPageList(CustomerPageReq req) {

        Page<CustomerPageResp> page = customerMapper.queryPageList(req.buildPage(), req);
        return PageResult.of(page.getRecords(), page.getTotal());
    }

    @Override
    public void addCustomer(CustomerReq req) {

        long count = customerMapper.selectCount(new LambdaQueryWrapper<CustomerDO>()
                .eq(CustomerDO::getPhone, req.getPhone()));
        if (count > 0) {
            throw new BusinessException("该手机号的客户已存在");
        }

        CustomerDO customerDO = new CustomerDO();
        BeanUtils.copyProperties(req, customerDO);
        customerMapper.insert(customerDO);
    }

    @Override
    public void updateCustomer(CustomerReq req) {

        if (req.getId() == null) {
            throw new BusinessException("客户id不能为空");
        }
        CustomerDO customerDO = customerMapper.selectById(req.getId());
        if (customerDO == null) {
            throw new BusinessException("客户不存在");
        }

        CustomerDO customerUpdate = new CustomerDO();
        BeanUtils.copyProperties(req, customerUpdate);
        customerMapper.updateById(customerUpdate);
    }

    @Override
    public CustomerPageResp queryCustomerById(Integer id) {

        CustomerPageResp customerPageResp = customerMapper.queryCustomerById(id);
        if (customerPageResp == null) {
            throw new BusinessException("客户不存在");
        }
        return customerPageResp;
    }
}
