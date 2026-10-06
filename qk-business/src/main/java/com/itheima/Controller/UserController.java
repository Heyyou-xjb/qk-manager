package com.itheima.Controller;

import com.itheima.dto.PageResult;
import com.itheima.dto.Result;
import com.itheima.model.dataobject.UserDO;
import com.itheima.model.req.UserAddReq;
import com.itheima.model.req.UserPageReq;
import com.itheima.model.req.UserUpdateReq;
import com.itheima.model.resp.UserResp;
import com.itheima.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author niubi
 * @since 2026-09-26
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Result<PageResult<UserResp>> queryPageList(UserPageReq req) {
        PageResult<UserResp> userRespPageResult = userService.queryPageList(req);
        return Result.success(userRespPageResult);
    }

    @DeleteMapping("/users/{ids}")
    public Result<Void> deleteByIds(@PathVariable List<Integer> ids) {
        return userService.removeBatchByIds(ids) ? Result.success() : Result.fail("删除失败");
    }

    @PostMapping("/users")
    public Result<Void> addUser(@RequestBody UserAddReq req) {
        int i = userService.addUser(req);
        return i > 0 ? Result.success() : Result.fail("添加失败");
    }

    @GetMapping("/users/{id}")
    public Result<UserDO> queryUserById(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    @PutMapping("/users")
    public Result<Void> updateUser(@RequestBody UserUpdateReq req) {
        return userService.updateUser(req) ? Result.success() : Result.fail("修改失败");
    }

    @GetMapping("/users/role/{roleLabel}")
    public Result<List<UserDO>> queryListByRoleLabel(@PathVariable String roleLabel) {
        return Result.success(userService.queryListByRoleLabel(roleLabel));
    }

    @GetMapping("/users/list")
    public Result<List<UserDO>> queryAllList() {
        return Result.success(userService.list());
    }

    @GetMapping("/users/dept/{deptId}")
    public Result<List<UserDO>> queryListByDeptId(@PathVariable Integer deptId) {
        return Result.success(userService.queryListByDeptId(deptId));
    }
}
