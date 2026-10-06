package com.itheima.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.PageResult;
import com.itheima.mapper.RoleMapper;
import com.itheima.model.dataobject.RoleDo;
import com.itheima.model.dataobject.UserDO;
import com.itheima.mapper.UserMapper;
import com.itheima.model.req.UserAddReq;
import com.itheima.model.req.UserPageReq;
import com.itheima.model.req.UserUpdateReq;
import com.itheima.model.resp.UserResp;
import com.itheima.service.UserService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author niubi
 * @since 2026-09-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageResult<UserResp> queryPageList(UserPageReq req) {

        // 创建page对象
        Page<UserResp> page = req.buildPage();

        // 分页列表查询
        Page<UserResp> userRespPage = userMapper.queryPageList(page, req);

        return PageResult.of(userRespPage.getRecords(), userRespPage.getTotal());
    }

    @Override
    public int addUser(UserAddReq req) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(req, userDO);
        userDO.setPassword(SecureUtil.md5(req.getUsername() + "123"));
        return userMapper.insert(userDO);
    }

    @Override
    public List<UserDO> queryListByRoleLabel(String roleLabel) {
        // 方式一：使用XML配置
        return userMapper.queryListByRoleLabel(roleLabel);

        // 方式二：把角色标签转换为角色ID
        /*RoleDo roleDo = roleMapper.selectOne(Wrappers.<RoleDo>lambdaQuery().eq(RoleDo::getLabel, roleLabel));
        Integer roleId = roleDo.getId();

        List<UserDO> userDOList = userMapper.selectList(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getRoleId, roleId));

        return userDOList;*/
    }

    @Override
    public boolean updateUser(UserUpdateReq req) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(req, userDO);
        return userMapper.updateById(userDO) > 0;
    }

    @Override
    public List<UserDO> queryListByDeptId(Integer deptId) {
        return userMapper.queryListByDeptId(deptId);
    }
}
