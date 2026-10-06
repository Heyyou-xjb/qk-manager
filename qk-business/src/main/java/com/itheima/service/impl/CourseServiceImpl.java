package com.itheima.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.PageResult;
import com.itheima.mapper.CourseMapper;
import com.itheima.model.dataobject.CourseDo;
import com.itheima.model.req.CourseAddReq;
import com.itheima.model.req.CourseUpdateReq;
import com.itheima.model.req.QueryStringReq;
import com.itheima.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public PageResult<CourseDo> queryCourseList(QueryStringReq req) {
        Page<CourseDo> page = req.buildPage();

        LambdaQueryWrapper<CourseDo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(req.getName()), CourseDo::getName, req.getName())
                .eq(req.getTarget() != null, CourseDo::getTarget, req.getTarget());

        Page<CourseDo> pageResult = courseMapper.selectPage(page, wrapper);

        return PageResult.of(pageResult.getRecords(), pageResult.getTotal());
    }

    @Override
    public int deleteCourse(Integer id) {
        return courseMapper.deleteById(id);
    }

    @Override
    public int addCourse(CourseAddReq req) {
        CourseDo course = new CourseDo();
        course.setName(req.getName());
        course.setSubject(req.getSubject());
        course.setTarget(req.getTarget());
        course.setDescription(req.getDescription());
        course.setPrice(req.getPrice());
        return courseMapper.insert(course);
    }

    @Override
    public CourseDo queryCourseById(Integer id) {
        return courseMapper.selectById(id);
    }

    @Override
    public int updateCourse(CourseUpdateReq req) {
        CourseDo course = new CourseDo();
        course.setId(req.getId());
        course.setName(req.getName());
        course.setSubject(req.getSubject());
        course.setTarget(req.getTarget());
        course.setDescription(req.getDescription());
        course.setPrice(req.getPrice());
        return courseMapper.updateById(course);
    }

    @Override
    public List<CourseDo> queryCourseList() {
        return courseMapper.selectList(null);
    }

    @Override
    public List<CourseDo> queryCourseListBySubject(Integer subject) {
        // 构建查询条件
        LambdaQueryWrapper<CourseDo> wrapper = new LambdaQueryWrapper<>();
        // 根据 subject 字段等值查询
        wrapper.eq(CourseDo::getSubject, subject);

        // 执行查询并返回列表
        return courseMapper.selectList(wrapper);
    }
}
