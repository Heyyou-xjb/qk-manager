package com.itheima.Controller;

import com.itheima.dto.PageResult;
import com.itheima.dto.Result;
import com.itheima.model.dataobject.ActivityDo;
import com.itheima.model.req.ActivityAddReq;
import com.itheima.model.req.ActivityPageReq;
import com.itheima.model.req.ActivityUpdateReq;
import com.itheima.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/activities")
    public Result<PageResult<ActivityDo>> getActivities(ActivityPageReq req) {
        log.info("getActivities");
        return Result.success(activityService.getActivities(req));
    }

    @DeleteMapping("/activities/{id}")
    public Result<Void> deleteActivityById(@PathVariable Integer id) {
        int i = activityService.deleteActivityById(id);
        return i > 0 ? Result.success() : Result.fail("删除失败");
    }

    @PostMapping("/activities")
    public Result<Void> addActivity(@RequestBody ActivityAddReq req) {
        int i = activityService.addActivity(req);
        return i > 0 ? Result.success() : Result.fail("添加失败");
    }

    @GetMapping("/activities/{id}")
    public Result<ActivityDo> getActivityById(@PathVariable Integer id) {
        return Result.success(activityService.getActivityById(id));
    }

    @PutMapping("/activities")
    public Result<Void> updateActivity(@RequestBody ActivityUpdateReq req) {
        int i = activityService.updateActivity(req);
        return i > 0 ? Result.success() : Result.fail("修改失败");
    }

    @GetMapping("/activities/type/{type}")
    public Result<List<ActivityDo>> getActivitiesByType(@PathVariable Integer type) {
        log.info("getActivitiesByType");
        List<ActivityDo> activities = activityService.getActivitiesByType(type);
        return Result.success(activities);
    }
}
