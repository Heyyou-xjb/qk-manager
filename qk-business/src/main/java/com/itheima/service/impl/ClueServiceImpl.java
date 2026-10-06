package com.itheima.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.PageResult;
import com.itheima.enums.ClueStatueEnum;
import com.itheima.exception.BusinessException;
import com.itheima.mapper.*;
import com.itheima.model.dataobject.*;
import com.itheima.model.req.*;
import com.itheima.model.resp.ClueInfoResp;
import com.itheima.model.resp.CluePageResp;
import com.itheima.model.resp.CluePoolResp;
import com.itheima.model.resp.ClueTrackRecordResp;
import com.itheima.service.ClueService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.itheima.utils.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * <p>
 * 线索表 服务实现类
 * </p>
 *
 * @author niubi
 * @since 2026-09-29
 */
@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, ClueDO> implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private ClueTrackRecordMapper clueTrackRecordMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public PageResult<CluePageResp> queryPageList(CluePageReq req) {

        Integer userId = UserHolder.getUserId();
        UserDO userDO = userMapper.selectById(userId);
        if (userDO == null) {
            throw new BusinessException("登录用户不存在，请重新登录");
        }

        Integer roleId = userDO.getRoleId();
        RoleDo roleDO = roleMapper.selectById(roleId);
        if (roleDO == null) {
            throw new BusinessException("角色不存在");
        }

        if ("admin".equals(roleDO.getLabel())) {
            userId = null;
        }

        Page<CluePageResp> cluePageRespPage = clueMapper.queryPageList(req.buildPage(), userId, req);
        return PageResult.of(cluePageRespPage.getRecords(), cluePageRespPage.getTotal());
    }

    @Override
    public void addClue(ClueAddReq req) {

        long count = clueMapper.selectCount(Wrappers.<ClueDO>lambdaQuery()
                .eq(ClueDO::getPhone, req.getPhone()));
        if (count > 0) {
            throw new BusinessException("该手机号已存在");
        }

        ClueDO clueDO = new ClueDO();
        BeanUtils.copyProperties(req, clueDO);
        clueMapper.insert(clueDO);
    }

    @Override
    public void assignClue(Integer clueId, Integer userId) {

        ClueDO clueDOCheck = clueMapper.selectById(clueId);
        if (clueDOCheck == null) {
            throw new BusinessException("线索不存在");
        }

        ClueDO clueDO = new ClueDO();
        clueDO.setId(clueId);

        clueDO.setUserId(userId);
        clueDO.setStatus(2);

        clueMapper.updateById(clueDO);
    }

    @Override
    public ClueInfoResp queryClue(Integer id) {

        ClueInfoResp clueInfoResp = clueMapper.queryClueById(id);
        if (clueInfoResp == null) {
            throw new BusinessException("线索不存在");
        }

        List<ClueTrackRecordResp> clueTrackRecordResps = clueTrackRecordMapper.queryListByClueId(id);
        clueInfoResp.setTrackRecords(clueTrackRecordResps);

        return clueInfoResp;
    }

    // 事务方法一：使用 @Transactional 注解
    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public void trackClue(ClueTrackReq req) {

        Integer id = req.getId();
        ClueDO clueDO = clueMapper.selectById(id);
        if (clueDO == null) {
            throw new BusinessException("线索不存在");
        }

        ClueDO clueDOUpdate = new ClueDO();
        BeanUtils.copyProperties(req, clueDOUpdate);
        clueDOUpdate.setStatus(3);
        clueMapper.updateById(clueDOUpdate);

        ClueTrackRecordDO clueTrackRecordDO = new ClueTrackRecordDO();
        clueTrackRecordDO.setClueId(req.getId());
        clueTrackRecordDO.setLevel(req.getLevel());
        clueTrackRecordDO.setSubject(req.getSubject());
        clueTrackRecordDO.setRecord(req.getRecord());
        clueTrackRecordDO.setNextTime(req.getNextTime());
        clueTrackRecordDO.setType(1);
        clueTrackRecordDO.setUserId(clueDO.getUserId());

        clueTrackRecordMapper.insert(clueTrackRecordDO);
    }*/

    // 事务方法二：使用 TransactionTemplate
    @Override
    public void trackClue(ClueTrackReq req) {

        Integer id = req.getId();
        ClueDO clueDO = clueMapper.selectById(id);
        if (clueDO == null) {
            throw new BusinessException("线索不存在");
        }

        transactionTemplate.execute(status -> {
            ClueDO clueDOUpdate = new ClueDO();
            BeanUtils.copyProperties(req, clueDOUpdate);
            clueDOUpdate.setStatus(3);
            clueMapper.updateById(clueDOUpdate);

            ClueTrackRecordDO clueTrackRecordDO = new ClueTrackRecordDO();
            clueTrackRecordDO.setClueId(req.getId());
            clueTrackRecordDO.setLevel(req.getLevel());
            clueTrackRecordDO.setSubject(req.getSubject());
            clueTrackRecordDO.setRecord(req.getRecord());
            clueTrackRecordDO.setNextTime(req.getNextTime());
            clueTrackRecordDO.setType(1);
            clueTrackRecordDO.setUserId(UserHolder.getUserId());

            clueTrackRecordMapper.insert(clueTrackRecordDO);
            return true;
        });
    }

    @Override
    public void convertFalse(Integer id, ClueFalseReq req) {
        // 检查线索是否存在
        ClueDO clueDO = clueMapper.selectById(id);
        if (clueDO == null) {
            throw new BusinessException("线索不存在");
        }

        transactionTemplate.execute(status -> {
            // 更改线索的状态 status == 4
            ClueDO clueUpdate = new ClueDO();
            clueUpdate.setId(id);
            clueUpdate.setStatus(4);
            clueMapper.updateById(clueUpdate);

            // 保存跟进记录
            ClueTrackRecordDO clueTrackRecordDO = new ClueTrackRecordDO();
            clueTrackRecordDO.setClueId(id);
            clueTrackRecordDO.setSubject(clueDO.getSubject());
            clueTrackRecordDO.setLevel(clueDO.getLevel());
            clueTrackRecordDO.setUserId(UserHolder.getUserId());
            clueTrackRecordDO.setType(0);
            clueTrackRecordDO.setRecord(req.getRemark());
            clueTrackRecordDO.setFalseReason(req.getReason());

            clueTrackRecordMapper.insert(clueTrackRecordDO);
            return true;
        });
    }

    @Override
    public void toBusiness(Integer id) {
        ClueDO clueDO = clueMapper.selectById(id);
        if (clueDO == null) {
            throw new BusinessException("线索不存在");
        }

        if (clueDO.getStatus() != ClueStatueEnum.FOLLOW_UP.getCode()) {
            throw new BusinessException("线索状态不是待转换");
        }

        transactionTemplate.execute(status -> {
            ClueDO clueUpdate = new ClueDO();
            clueUpdate.setId(id);
            clueUpdate.setStatus(ClueStatueEnum.TRANSFORM.getCode());
            clueMapper.updateById(clueUpdate);

            BusinessDO businessDO = new BusinessDO();
            BeanUtils.copyProperties(clueDO, businessDO);
            businessDO.setClueId(id);
            businessDO.setId(null);
            businessDO.setStatus(ClueStatueEnum.ASSIGN.getCode());
            businessDO.setUserId(null);
            businessDO.setNextTime(null);
            businessDO.setCreateTime(null);

            businessMapper.insert(businessDO);
            return true;
        });
    }

    @Override
    public PageResult<CluePoolResp> queryPoolList(CluePoolReq req) {

        //线索池存放的是尚未分配出去的“待分配”线索，所有人都能查看，不需要按归属人过滤
        Page<CluePoolResp> cluePoolRespPage = clueMapper.queryPoolList(req.buildPage(), req);
        return PageResult.of(cluePoolRespPage.getRecords(), cluePoolRespPage.getTotal());
    }
}
