package com.itheima.Controller;

import com.itheima.aop.Log;
import com.itheima.dto.PageResult;
import com.itheima.dto.Result;
import com.itheima.model.dataobject.ClueDO;
import com.itheima.model.req.*;
import com.itheima.model.resp.ClueInfoResp;
import com.itheima.model.resp.CluePageResp;
import com.itheima.model.resp.CluePoolResp;
import com.itheima.service.ClueService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 线索表 前端控制器
 * </p>
 *
 * @author niubi
 * @since 2026-09-29
 */
@RestController
public class ClueController {

    @Autowired
    private ClueService clueService;
    @Log
    @GetMapping("/clues")
    public Result<PageResult<CluePageResp>> queryPageList(CluePageReq req) {
        PageResult<CluePageResp> pageResult = clueService.queryPageList(req);
        return Result.success(pageResult);
    }

    @PostMapping("/clues")
    public Result<Void> addClue(@RequestBody @Valid ClueAddReq req) {
        clueService.addClue(req);
        return Result.success();
    }

    @PutMapping("/clues/assign/{clueId}/{userId}")
    public Result<Void> assignClue(@PathVariable Integer clueId, @PathVariable Integer userId) {
        clueService.assignClue(clueId, userId);
        return Result.success();
    }

    @GetMapping("/clues/{id}")
    public Result<ClueInfoResp> queryClue(@PathVariable Integer id) {
        ClueInfoResp clueInfoResp = clueService.queryClue(id);
        return Result.success(clueInfoResp);
    }

    @PutMapping("/clues")
    public Result<Void> trackClue(@RequestBody ClueTrackReq req) {
        clueService.trackClue(req);
        return Result.success();
    }

    @PutMapping("/clues/false/{id}")
    public Result<Void> convertFalse(@PathVariable Integer id, @RequestBody ClueFalseReq req) {
        clueService.convertFalse(id, req);
        return Result.success();
    }

    @PutMapping("/clues/toBusiness/{id}")
    public Result<Void> toBusiness(@PathVariable Integer id) {
        clueService.toBusiness(id);
        return Result.success();
    }

    @GetMapping("/clues/pool")
    public Result<PageResult<CluePoolResp>> queryPoolList(CluePoolReq req) {
        PageResult<CluePoolResp> pageResult = clueService.queryPoolList(req);
        return Result.success(pageResult);
    }
}
