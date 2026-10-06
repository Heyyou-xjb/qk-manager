package com.itheima.service;

import com.itheima.dto.PageResult;
import com.itheima.model.dataobject.CourseDo;
import com.itheima.model.req.CourseAddReq;
import com.itheima.model.req.CourseUpdateReq;
import com.itheima.model.req.QueryStringReq;

import java.util.List;

public interface CourseService {
    PageResult<CourseDo> queryCourseList(QueryStringReq req);

    int deleteCourse(Integer id);

    int addCourse(CourseAddReq req);

    CourseDo queryCourseById(Integer id);

    int updateCourse(CourseUpdateReq req);

    List<CourseDo> queryCourseList();

    List<CourseDo> queryCourseListBySubject(Integer subject);

}
