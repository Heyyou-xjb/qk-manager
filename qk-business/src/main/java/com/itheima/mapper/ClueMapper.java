package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.model.dataobject.ClueDO;
import com.itheima.model.req.CluePageReq;
import com.itheima.model.req.CluePoolReq;
import com.itheima.model.resp.ClueInfoResp;
import com.itheima.model.resp.CluePageResp;
import com.itheima.model.resp.CluePoolResp;
import com.itheima.model.resp.OverviewResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 线索表 Mapper 接口
 * </p>
 *
 * @author niubi
 * @since 2026-09-29
 */
@Mapper
public interface ClueMapper extends BaseMapper<ClueDO> {
    Page<CluePageResp> queryPageList(Page<CluePageResp> page, Integer userId,CluePageReq req);

    Page<CluePoolResp> queryPoolList(@Param("page") Page<CluePoolResp> page, @Param("req") CluePoolReq req);

    ClueInfoResp queryClueById(Integer id);

    OverviewResp countClue();
}
