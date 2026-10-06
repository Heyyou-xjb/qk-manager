package com.itheima.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.PageResult;
import com.itheima.exception.BusinessException;
import com.itheima.mapper.BusinessMapper;
import com.itheima.mapper.BusinessTrackRecordMapper;
import com.itheima.mapper.CustomerMapper;
import com.itheima.mapper.RoleMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.model.dataobject.BusinessDO;
import com.itheima.model.dataobject.BusinessTrackRecordDO;
import com.itheima.model.dataobject.CustomerDO;
import com.itheima.model.dataobject.RoleDo;
import com.itheima.model.dataobject.UserDO;
import com.itheima.model.req.BusinessAddReq;
import com.itheima.model.req.BusinessPageReq;
import com.itheima.model.req.BusinessPoolReq;
import com.itheima.model.req.BusinessUpdateReq;
import com.itheima.model.resp.BusinessInfoResp;
import com.itheima.model.resp.BusinessPageResp;
import com.itheima.service.BusinessService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.itheima.utils.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 商机表 服务实现类
 * </p>
 *
 * @author niubi
 * @since 2026-09-29
 */
@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, BusinessDO> implements BusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private BusinessTrackRecordMapper businessTrackRecordMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageResult<BusinessPageResp> getBusinessList(BusinessPageReq req) {

        //数据权限：管理员看全部商机，商机专员只看归属自己的商机
        Integer userId = currentUserIdIfNotAdmin();

        Page<BusinessPageResp> page = businessMapper.getBusinessList(req.buildPage(), userId, req);
        return PageResult.of(page.getRecords(), page.getTotal());
    }

    @Override
    public PageResult<BusinessPageResp> getBusinessPoolList(BusinessPoolReq req) {

        //商机公海存放的是尚未分配给任何人的“待分配”商机，所有人都能查看，不需要按归属人过滤
        Page<BusinessPageResp> page = businessMapper.getBusinessPoolList(req.buildPage(), req);
        return PageResult.of(page.getRecords(), page.getTotal());
    }

    @Override
    public BusinessInfoResp getBusinessById(Integer id) {

        BusinessInfoResp businessInfoResp = businessMapper.getBusinessInfoById(id);
        if (businessInfoResp == null) {
            throw new BusinessException("商机不存在");
        }
        businessInfoResp.setTrackRecords(businessTrackRecordMapper.queryListByBusinessId(id));
        return businessInfoResp;
    }

    @Override
    public void addBusiness(BusinessAddReq req) {

        long count = businessMapper.selectCount(new LambdaQueryWrapper<BusinessDO>()
                .eq(BusinessDO::getPhone, req.getPhone()));

        if (count > 0) {
            throw new BusinessException("商机已存在!");
        }

        BusinessDO businessDO = new BusinessDO();
        BeanUtils.copyProperties(req, businessDO);
        //新增的商机进入公海，等待管理员分配
        businessDO.setStatus(1);
        businessMapper.insert(businessDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBusiness(BusinessUpdateReq req) {

        if (req.getId() == null) {
            throw new BusinessException("商机id不能为空");
        }
        BusinessDO businessDO = businessMapper.selectById(req.getId());
        if (businessDO == null) {
            throw new BusinessException("商机不存在");
        }

        //1. 更新商机：状态改为跟进中，记录下次跟进时间
        BusinessDO businessUpdate = new BusinessDO();
        BeanUtils.copyProperties(req, businessUpdate);
        businessUpdate.setStatus(3);
        businessUpdate.setNextTime(parseNextTime(req.getNextTime()));
        businessMapper.updateById(businessUpdate);

        //2. 保存本次跟进记录
        BusinessTrackRecordDO trackRecordDO = new BusinessTrackRecordDO();
        trackRecordDO.setBusinessId(req.getId());
        trackRecordDO.setUserId(UserHolder.getUserId());
        trackRecordDO.setTrackStatus(req.getTrackStatus());
        trackRecordDO.setKeyItems(CollUtil.isEmpty(req.getKeyItems()) ? null : CollUtil.join(req.getKeyItems(), ","));
        trackRecordDO.setNextTime(businessUpdate.getNextTime());
        trackRecordDO.setRecord(req.getRecord());
        trackRecordDO.setCreateTime(LocalDateTime.now());
        businessTrackRecordMapper.insert(trackRecordDO);
    }

    @Override
    public void assignBusiness(Integer id, Integer userId) {

        BusinessDO businessDO = businessMapper.selectById(id);
        if (businessDO == null) {
            throw new BusinessException("商机不存在");
        }

        BusinessDO businessUpdate = new BusinessDO();
        businessUpdate.setId(id);
        businessUpdate.setUserId(userId);
        businessUpdate.setStatus(2);
        businessMapper.updateById(businessUpdate);
    }

    @Override
    public void backBusiness(Integer id) {

        BusinessDO businessDO = businessMapper.selectById(id);
        if (businessDO == null) {
            throw new BusinessException("商机不存在");
        }

        //退回公海：清空归属人与下次跟进时间，状态回到待分配
        businessMapper.update(null, Wrappers.<BusinessDO>lambdaUpdate()
                .eq(BusinessDO::getId, id)
                .set(BusinessDO::getUserId, null)
                .set(BusinessDO::getNextTime, null)
                .set(BusinessDO::getStatus, 1)
                .set(BusinessDO::getUpdateTime, LocalDateTime.now()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toCustomer(Integer id) {

        BusinessDO businessDO = businessMapper.selectById(id);
        if (businessDO == null) {
            throw new BusinessException("商机不存在");
        }

        Long count = customerMapper.selectCount(new LambdaQueryWrapper<CustomerDO>()
                .eq(CustomerDO::getBusinessId, id));
        if (count > 0) {
            throw new BusinessException("该商机已转为客户");
        }

        //1. 商机信息复制到客户表
        CustomerDO customerDO = new CustomerDO();
        BeanUtils.copyProperties(businessDO, customerDO);
        customerDO.setId(null);
        customerDO.setBusinessId(businessDO.getId());
        customerDO.setCreateTime(LocalDateTime.now());
        customerDO.setUpdateTime(LocalDateTime.now());
        customerMapper.insert(customerDO);

        //2. 商机状态改为转客户
        BusinessDO businessUpdate = new BusinessDO();
        businessUpdate.setId(id);
        businessUpdate.setStatus(5);
        businessMapper.updateById(businessUpdate);
    }

    /**
     * 当前登录用户id；如果是管理员返回 null，表示查询时不限制归属人
     */
    private Integer currentUserIdIfNotAdmin() {

        Integer userId = UserHolder.getUserId();
        UserDO userDO = userMapper.selectById(userId);
        if (userDO == null) {
            throw new BusinessException("登录用户不存在，请重新登录");
        }

        RoleDo roleDO = roleMapper.selectById(userDO.getRoleId());
        if (roleDO == null) {
            throw new BusinessException("角色不存在");
        }
        return "admin".equals(roleDO.getLabel()) ? null : userId;
    }

    /**
     * 前端传的是 yyyy-MM-dd'T'HH:mm:ss 字符串，空字符串表示不设置下次跟进时间
     */
    private LocalDateTime parseNextTime(String nextTime) {

        if (StrUtil.isBlank(nextTime)) {
            return null;
        }
        try {
            return LocalDateTime.parse(nextTime);
        } catch (Exception e) {
            throw new BusinessException("下次跟进时间格式不正确");
        }
    }
}
