package com.itheima.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.itheima.dto.PageResult;
import com.itheima.exception.BusinessException;
import com.itheima.mapper.RoleMapper;
import com.itheima.model.dataobject.RoleDo;
import com.itheima.model.req.RoleAddReq;
import com.itheima.model.req.RolePageReq;
import com.itheima.model.req.RoleUpdateReq;
import com.itheima.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDo> implements RoleService {

    @Override
    public PageResult<RoleDo> getRoleList(RolePageReq req) {
        Page<RoleDo> page = req.buildPage();
        Page<RoleDo> result = page(page, Wrappers.<RoleDo>lambdaQuery()
                .like(StrUtil.isNotBlank(req.getName()), RoleDo::getName, req.getName())
                .eq(StrUtil.isNotBlank(req.getLabel()), RoleDo::getLabel, req.getLabel())
                .orderByDesc(RoleDo::getId));


        return PageResult.of(result.getRecords(), result.getTotal());
    }

    @Override
    public boolean addRole(RoleAddReq req) {
        RoleDo roleDo = new RoleDo();
        roleDo.setName(req.getName());
        roleDo.setLabel(req.getLabel());
        roleDo.setRemark(req.getRemark());
        roleDo.setCreateTime(LocalDateTime.now());
        roleDo.setUpdateTime(LocalDateTime.now());

        long count = count(Wrappers.<RoleDo>lambdaQuery().eq(RoleDo::getName, req.getName()));
        if (count > 0) {
            throw new BusinessException("角色名已存在");
        }

        long count1 = count(Wrappers.<RoleDo>lambdaQuery().eq(RoleDo::getLabel, req.getLabel()));
        if (count1 > 0) {
            throw new BusinessException("角色标签已存在");
        }

        if (!save(roleDo)) {
            throw new BusinessException("添加失败");
        }
        return true;
    }

    @Override
    public void updateRole(RoleUpdateReq req) {
        // 排除当前角色
        long roleNameCount = count(Wrappers.<RoleDo>lambdaQuery()
                .eq(RoleDo::getName, req.getName())
                .ne(RoleDo::getId, req.getId()));
        if (roleNameCount > 0) {
            throw new BusinessException("角色名已存在");
        }

        long count1 = count(Wrappers.<RoleDo>lambdaQuery()
                .eq(RoleDo::getLabel, req.getLabel())
                .ne(RoleDo::getId, req.getId()));
        if (count1 > 0) {
            throw new BusinessException("角色标签已存在");
        }

        RoleDo roleDo = new RoleDo();
        BeanUtils.copyProperties(req, roleDo);
        roleDo.setUpdateTime(LocalDateTime.now());

        if (!updateById(roleDo)) {
            throw new BusinessException("修改失败");
        }
    }
}
