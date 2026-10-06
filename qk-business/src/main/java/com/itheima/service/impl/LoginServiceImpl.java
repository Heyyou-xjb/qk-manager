package com.itheima.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itheima.exception.BusinessException;
import com.itheima.mapper.RoleMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.model.dataobject.RoleDo;
import com.itheima.model.dataobject.UserDO;
import com.itheima.model.req.LoginReq;
import com.itheima.model.resp.LoginResultResp;
import com.itheima.service.LoginService;
import com.itheima.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResultResp login(LoginReq req) {

        String username = req.getUsername();
        String password = req.getPassword();

        UserDO userDO = userMapper.selectOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getUsername, username));
        if (userDO == null) {
            log.warn("用户名错误 {}", username);
            throw new BusinessException("用户名错误");
        }

        if (!Objects.equals(SecureUtil.md5(password), userDO.getPassword())) {
            log.warn("密码错误 {}", password);
            throw new BusinessException("密码错误");
        }


        RoleDo roleDO = roleMapper.selectById(userDO.getRoleId());
        if (roleDO == null) {
            log.warn("角色不存在 {}", userDO.getRoleId());
            throw new BusinessException("角色不存在");
        }

        LoginResultResp loginResultResp = new LoginResultResp();
        BeanUtils.copyProperties(userDO, loginResultResp);
        loginResultResp.setRoleLabel(roleDO.getLabel());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDO.getId());
        String token = jwtUtil.generateToken(claims);

        loginResultResp.setToken(token);

        return loginResultResp;
    }
}
