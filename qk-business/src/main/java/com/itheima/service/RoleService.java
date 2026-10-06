package com.itheima.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.itheima.dto.PageResult;
import com.itheima.model.dataobject.RoleDo;
import com.itheima.model.req.RoleAddReq;
import com.itheima.model.req.RolePageReq;
import com.itheima.model.req.RoleUpdateReq;

public interface RoleService extends IService<RoleDo> {
    PageResult<RoleDo> getRoleList(RolePageReq req);

    boolean addRole(RoleAddReq req);

    void updateRole(RoleUpdateReq req);
}
