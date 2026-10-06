package com.itheima.service.impl;

import com.itheima.model.dataobject.OperateLogDO;
import com.itheima.mapper.OperateLogMapper;
import com.itheima.service.OperateLogService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.PageResult;
import com.itheima.model.req.OperateLogPageReq;
import com.itheima.model.resp.OperateLogResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author niubi
 * @since 2026-09-30
 */
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLogDO> implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public PageResult<OperateLogResp> queryPageList(OperateLogPageReq req) {

        Page<OperateLogResp> page = operateLogMapper.queryPageList(req.buildPage(), req);
        return PageResult.of(page.getRecords(), page.getTotal());
    }
}
