package com.itheima.Controller;

import com.itheima.dto.PageResult;
import com.itheima.dto.Result;
import com.itheima.model.req.CustomerPageReq;
import com.itheima.model.req.CustomerReq;
import com.itheima.model.resp.CustomerPageResp;
import com.itheima.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 客户表 前端控制器
 * </p>
 *
 * @author niubi
 * @since 2026-10-05
 */
@Slf4j
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public Result<PageResult<CustomerPageResp>> queryPageList(CustomerPageReq req) {
        return Result.success(customerService.queryPageList(req));
    }

    @PostMapping("/customers")
    public Result<Void> addCustomer(@RequestBody @Valid CustomerReq req) {
        customerService.addCustomer(req);
        return Result.success();
    }

    @GetMapping("/customers/{id}")
    public Result<CustomerPageResp> queryCustomer(@PathVariable Integer id) {
        return Result.success(customerService.queryCustomerById(id));
    }

    @PutMapping("/customers")
    public Result<Void> updateCustomer(@RequestBody @Valid CustomerReq req) {
        customerService.updateCustomer(req);
        return Result.success();
    }
}
