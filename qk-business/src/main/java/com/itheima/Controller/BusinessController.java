package com.itheima.Controller;

import com.itheima.dto.PageResult;
import com.itheima.dto.Result;
import com.itheima.model.req.BusinessAddReq;
import com.itheima.model.req.BusinessPageReq;
import com.itheima.model.req.BusinessPoolReq;
import com.itheima.model.req.BusinessUpdateReq;
import com.itheima.model.resp.BusinessInfoResp;
import com.itheima.model.resp.BusinessPageResp;
import com.itheima.service.BusinessService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商机表 前端控制器
 * </p>
 *
 * @author niubi
 * @since 2026-09-29
 */
@RestController
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/businesses")
    public Result<PageResult<BusinessPageResp>> getBusinessList(BusinessPageReq req) {
        return Result.success(businessService.getBusinessList(req));
    }

    @GetMapping("/businesses/pool")
    public Result<PageResult<BusinessPageResp>> getBusinessPoolList(BusinessPoolReq req) {
        return Result.success(businessService.getBusinessPoolList(req));
    }

    @GetMapping("/businesses/{id}")
    public Result<BusinessInfoResp> getBusinessById(@PathVariable Integer id) {
        return Result.success(businessService.getBusinessById(id));
    }

    @PostMapping("/businesses")
    public Result<Void> addBusiness(@RequestBody @Valid BusinessAddReq req) {
        businessService.addBusiness(req);
        return Result.success();
    }

    @PutMapping("/businesses")
    public Result<Void> updateBusiness(@RequestBody BusinessUpdateReq req) {
        businessService.updateBusiness(req);
        return Result.success();
    }

    @PutMapping("/businesses/assign/{businessId}/{userId}")
    public Result<Void> assignBusiness(@PathVariable Integer businessId, @PathVariable Integer userId) {
        businessService.assignBusiness(businessId, userId);
        return Result.success();
    }

    @PutMapping("/businesses/back/{id}")
    public Result<Void> backBusiness(@PathVariable Integer id) {
        businessService.backBusiness(id);
        return Result.success();
    }

    @PostMapping("/businesses/toCustomer/{id}")
    public Result<Void> toCustomer(@PathVariable Integer id) {
        businessService.toCustomer(id);
        return Result.success();
    }
}
