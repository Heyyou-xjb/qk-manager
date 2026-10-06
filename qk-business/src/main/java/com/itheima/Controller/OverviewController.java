package com.itheima.Controller;

import com.itheima.dto.Result;
import com.itheima.model.resp.OverviewResp;
import com.itheima.service.OverviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OverviewController {

    @Autowired
    private OverviewService overviewService; // Changed from private OverviewService overviewService;

    @GetMapping("/report/overview")
    public Result countClueAndBusiness() {
        OverviewResp overviewResp = overviewService.countClueAndBusiness();
        return Result.success(overviewResp);
    }

}
