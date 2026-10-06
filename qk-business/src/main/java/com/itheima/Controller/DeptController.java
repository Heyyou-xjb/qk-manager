package com.itheima.Controller;

import com.itheima.dto.PageResult;
import com.itheima.dto.Result;
import com.itheima.model.dataobject.DeptDO;
import com.itheima.model.req.DeptAddReq;
import com.itheima.model.req.DeptPageReq;
import com.itheima.model.req.DeptUpdateReq;
import com.itheima.service.DeptService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/depts") //接收前端发送的GET请求
    public Result<PageResult<DeptDO>> listDepts(DeptPageReq req) {
        log.info("listDepts: {}", req);
        PageResult<DeptDO> pageResult = deptService.findDeptsByPage(req);
        return Result.success(pageResult);
    }

    @DeleteMapping("/depts/{id}")
    public Result<Void> deleteDept(@PathVariable Integer id) {

        int i = deptService.deleteDept(id);
        return i > 0 ? Result.success() : Result.fail("删除失败");
    }

    @PostMapping("/depts")
    public Result<Void> addDept(@RequestBody DeptAddReq req) {
        log.info("addDept: {}", req);
        int i = deptService.addDept(req);
        return i > 0 ? Result.success() : Result.fail("添加失败");
    }

    @GetMapping("/depts/{id}")
    public Result<DeptDO> queryDept(@PathVariable Integer id) {
        DeptDO deptDO = deptService.queryDept(id);
        return Result.success(deptDO);
    }

    @PutMapping("/depts")
    public Result<Void> updateDept(@RequestBody @Valid DeptUpdateReq req) {
        log.info("updateDept: {}", req);
        int i = deptService.updateDept(req);
        return i > 0 ? Result.success() : Result.fail("修改失败");
    }

    @GetMapping("/depts/list")
    public Result<List<DeptDO>> listAllDept() {
        List<DeptDO> deptDOList = deptService.listAllDept();
        return Result.success(deptDOList);
    }
}
