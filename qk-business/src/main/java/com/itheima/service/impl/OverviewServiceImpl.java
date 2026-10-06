package com.itheima.service.impl;

import com.itheima.mapper.BusinessMapper;
import com.itheima.mapper.ClueMapper;
import com.itheima.model.resp.OverviewResp;
import com.itheima.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OverviewServiceImpl implements OverviewService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private BusinessMapper businessMapper;

    @Override
    public OverviewResp countClueAndBusiness() {
        OverviewResp clueResp = clueMapper.countClue();

        OverviewResp businessResp = businessMapper.countBusiness();

        clueResp.setBusinessTotal(businessResp.getBusinessTotal());
        clueResp.setBusinessWaitAllot(businessResp.getBusinessWaitAllot());
        clueResp.setBusinessWaitFollow(businessResp.getBusinessWaitFollow());
        clueResp.setBusinessFollowing(businessResp.getBusinessFollowing());
        clueResp.setBusinessFalse(businessResp.getBusinessFalse());
        clueResp.setBusinessConvertCustomer(businessResp.getBusinessConvertCustomer());

        return clueResp;
    }
}
