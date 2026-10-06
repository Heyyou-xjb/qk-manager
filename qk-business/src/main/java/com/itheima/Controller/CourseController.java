package com.itheima.Controller;

import com.itheima.dto.PageResult;
import com.itheima.dto.Result;
import com.itheima.model.dataobject.CourseDo;
import com.itheima.model.req.CourseAddReq;
import com.itheima.model.req.CourseUpdateReq;
import com.itheima.model.req.QueryStringReq;
import com.itheima.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public Result<PageResult<CourseDo>> queryCourseList(QueryStringReq req) {
        return Result.success(courseService.queryCourseList(req));
    }

    @DeleteMapping("/courses/{id}")
    public Result<Void> deleteCourse(@PathVariable("id") Integer id) {
        int i = courseService.deleteCourse(id);
        return i > 0 ? Result.success() : Result.fail("删除失败");
    }

    @PostMapping("/courses")
    public Result<Void> addCourse(@RequestBody CourseAddReq req) {
        int i = courseService.addCourse(req);
        return i > 0 ? Result.success() : Result.fail("添加失败");
    }

    @GetMapping("/courses/{id}")
    public Result<CourseDo> queryCourseById(@PathVariable Integer id) {
        return Result.success(courseService.queryCourseById(id));
    }

    @PutMapping("/courses")
    public Result<Void> updateCourse(@RequestBody CourseUpdateReq req) {
        int i = courseService.updateCourse(req);
        return i > 0 ? Result.success() : Result.fail("修改失败");
    }

    @GetMapping("/courses/list")
    public Result<List<CourseDo>> queryCourseList() {
        return Result.success(courseService.queryCourseList());
    }

    @GetMapping("/courses/subject/{subject}")
    public Result<List<CourseDo>> queryCourseListBySubject(@PathVariable Integer subject) {
        return Result.success(courseService.queryCourseListBySubject(subject));
    }
}
