package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.itheima.dto.PageResult;
import com.itheima.mapper.ActivityMapper;
import com.itheima.model.dataobject.ActivityDo;
import com.itheima.model.req.ActivityAddReq;
import com.itheima.model.req.ActivityPageReq;
import com.itheima.model.req.ActivityUpdateReq;
import com.itheima.service.ActivityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityDo> implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public PageResult<ActivityDo> getActivities(ActivityPageReq req) {
        Page<ActivityDo> page = req.buildPage();

        LambdaQueryWrapper<ActivityDo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(req.getChannel() != null, ActivityDo::getChannel, req.getChannel())
                .eq(req.getType() != null, ActivityDo::getType, req.getType());

        Page<ActivityDo> result = activityMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal());
    }

    @Override
    public int deleteActivityById(Integer id) {
        return activityMapper.deleteById(id);
    }

    @Override
    public int addActivity(ActivityAddReq req) {
        ActivityDo activityDo = new ActivityDo();
        BeanUtils.copyProperties(req, activityDo);
        return activityMapper.insert(activityDo);
    }

    @Override
    public ActivityDo getActivityById(Integer id) {
        return activityMapper.selectById(id);
    }

    @Override
    public int updateActivity(ActivityUpdateReq req) {
        ActivityDo activityDo = new ActivityDo();
        BeanUtils.copyProperties(req, activityDo);
        return activityMapper.updateById(activityDo);
    }

    @Override
    public List<ActivityDo> getActivitiesByType(Integer type) {
        LambdaQueryWrapper<ActivityDo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityDo::getType, type);
        return activityMapper.selectList(wrapper);
    }
}
