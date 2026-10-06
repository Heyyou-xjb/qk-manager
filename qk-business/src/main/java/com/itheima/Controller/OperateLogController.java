package com.itheima.Controller;

import com.itheima.dto.PageResult;
import com.itheima.dto.Result;
import com.itheima.model.req.OperateLogPageReq;
import com.itheima.model.resp.OperateLogResp;
import com.itheima.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 操作日志表 前端控制器
 * </p>
 *
 * @author niubi
 * @since 2026-09-30
 */
@RestController
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;

    @GetMapping("/logs")
    public Result<PageResult<OperateLogResp>> queryPageList(OperateLogPageReq req) {
        return Result.success(operateLogService.queryPageList(req));
    }
}
