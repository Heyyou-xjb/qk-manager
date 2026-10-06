package com.itheima.service;

import com.itheima.dto.PageResult;
import com.itheima.model.dataobject.UserDO;
import com.baomidou.mybatisplus.spring.service.IService;
import com.itheima.model.req.UserAddReq;
import com.itheima.model.req.UserPageReq;
import com.itheima.model.req.UserUpdateReq;
import com.itheima.model.resp.UserResp;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author niubi
 * @since 2026-09-26
 */
public interface UserService extends IService<UserDO> {

    PageResult<UserResp> queryPageList(UserPageReq req);

    int addUser(@RequestBody UserAddReq req);

    List<UserDO> queryListByRoleLabel(@PathVariable String roleLabel);

    boolean updateUser(UserUpdateReq req);

    List<UserDO> queryListByDeptId(Integer deptId);

}
