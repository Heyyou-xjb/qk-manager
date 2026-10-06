package com.itheima.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.itheima.dto.PageResult;
import com.itheima.model.dataobject.ActivityDo;
import com.itheima.model.req.ActivityAddReq;
import com.itheima.model.req.ActivityPageReq;
import com.itheima.model.req.ActivityUpdateReq;

import java.util.List;

public interface ActivityService extends IService<ActivityDo> {

    PageResult<ActivityDo> getActivities(ActivityPageReq req);

    int deleteActivityById(Integer id);

    int addActivity(ActivityAddReq req);

    ActivityDo getActivityById(Integer id);

    int updateActivity(ActivityUpdateReq req);

    List<ActivityDo> getActivitiesByType(Integer type);
}
