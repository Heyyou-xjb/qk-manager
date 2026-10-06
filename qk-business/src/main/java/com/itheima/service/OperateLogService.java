package com.itheima.service;

import com.itheima.model.dataobject.OperateLogDO;
import com.baomidou.mybatisplus.spring.service.IService;
import com.itheima.dto.PageResult;
import com.itheima.model.req.OperateLogPageReq;
import com.itheima.model.resp.OperateLogResp;

/**
 * <p>
 * 操作日志表 服务类
 * </p>
 *
 * @author niubi
 * @since 2026-09-30
 */
public interface OperateLogService extends IService<OperateLogDO> {

    PageResult<OperateLogResp> queryPageList(OperateLogPageReq req);

}
