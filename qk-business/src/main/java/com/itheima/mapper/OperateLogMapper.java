package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.model.dataobject.OperateLogDO;
import com.itheima.model.req.OperateLogPageReq;
import com.itheima.model.resp.OperateLogResp;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 操作日志表 Mapper 接口
 * </p>
 *
 * @author niubi
 * @since 2026-09-30
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLogDO> {

    Page<OperateLogResp> queryPageList(Page<OperateLogResp> page, OperateLogPageReq req);

}
