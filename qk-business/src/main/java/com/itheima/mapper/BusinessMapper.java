package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.model.dataobject.BusinessDO;
import com.itheima.model.req.BusinessPageReq;
import com.itheima.model.req.BusinessPoolReq;
import com.itheima.model.resp.BusinessInfoResp;
import com.itheima.model.resp.BusinessPageResp;
import com.itheima.model.resp.OverviewResp;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商机表 Mapper 接口
 * </p>
 *
 * @author niubi
 * @since 2026-09-29
 */
@Mapper
public interface BusinessMapper extends BaseMapper<BusinessDO> {

    Page<BusinessPageResp> getBusinessList(Page<BusinessPageResp> page, Integer userId, BusinessPageReq req);

    Page<BusinessPageResp> getBusinessPoolList(Page<BusinessPageResp> page, BusinessPoolReq req);

    BusinessInfoResp getBusinessInfoById(Integer id);

    OverviewResp countBusiness();

}
