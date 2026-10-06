package com.itheima.service;

import com.itheima.dto.PageResult;
import com.itheima.model.dataobject.ClueDO;
import com.baomidou.mybatisplus.spring.service.IService;
import com.itheima.model.req.*;
import com.itheima.model.resp.ClueInfoResp;
import com.itheima.model.resp.CluePageResp;
import com.itheima.model.resp.CluePoolResp;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 线索表 服务类
 * </p>
 *
 * @author niubi
 * @since 2026-09-29
 */
public interface ClueService extends IService<ClueDO> {

    PageResult<CluePageResp> queryPageList(CluePageReq req);

    void addClue(@RequestBody @Valid ClueAddReq req);

    void assignClue(Integer clueId, Integer userId);

    ClueInfoResp queryClue(Integer id);

    void trackClue(ClueTrackReq req);

    void convertFalse(Integer id, ClueFalseReq req);

    void toBusiness(Integer id);

    PageResult<CluePoolResp> queryPoolList(CluePoolReq req);
}
