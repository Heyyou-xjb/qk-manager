package com.itheima.Controller;

import com.itheima.dto.PageResult;


import com.itheima.dto.Result;
import com.itheima.model.dataobject.RoleDo;
import com.itheima.model.req.RoleAddReq;
import com.itheima.model.req.RolePageReq;
import com.itheima.model.req.RoleUpdateReq;
import com.itheima.service.RoleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/roles")
    public Result<PageResult<RoleDo>> getRoleList(RolePageReq req) {
        return Result.success(roleService.getRoleList(req));
    }

    @DeleteMapping("/roles/{id}")
    public Result<Void> deleteRole(@PathVariable Integer id) {
        return roleService.removeById(id) ? Result.success() : Result.fail("删除失败");
    }

    @PostMapping("/roles")
    public Result<Void> addRole(@RequestBody @Valid RoleAddReq req) {
        return roleService.addRole(req) ? Result.success() : Result.fail("添加失败");
    }

    @GetMapping("/roles/{id}")
    public Result<RoleDo> getRole(@PathVariable Integer id) {
        RoleDo roleDo = roleService.getById(id);
        return Result.success(roleDo);
    }

    @PutMapping("/roles")
    public Result<Void> updateRole(@RequestBody @Valid RoleUpdateReq req) {
        roleService.updateRole(req);
        return Result.success();
    }

    @GetMapping("/roles/list")
    public Result<List<RoleDo>> queryAllList() {
        List<RoleDo> list = roleService.list();
        return Result.success(list);
    }
}
