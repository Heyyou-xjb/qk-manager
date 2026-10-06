package com.itheima.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.PageResult;
import com.itheima.exception.BusinessException;
import com.itheima.mapper.DeptMapper;
import com.itheima.model.dataobject.DeptDO;
import com.itheima.model.req.DeptAddReq;
import com.itheima.model.req.DeptPageReq;
import com.itheima.model.req.DeptUpdateReq;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public PageResult<DeptDO> findDeptsByPage(DeptPageReq req) {
        //分页插件使用步骤
        //1.设置分页参数
        Page<DeptDO> page = req.buildPage();

        //2. 设置条件参数
        LambdaQueryWrapper<DeptDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(req.getName()), DeptDO::getName, req.getName())
                .eq(req.getStatus() != null, DeptDO::getStatus, req.getStatus());

        //3. 执行分页查询
        Page<DeptDO> pageResult = deptMapper.selectPage(page, wrapper);

        //4. 返回结果
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal());
    }

    @Override
    public int deleteDept(Integer id) {
        return deptMapper.deleteById(id);
    }

    @Override
    public int addDept(DeptAddReq req) {

        long count = deptMapper.selectCount(new LambdaQueryWrapper<DeptDO>()
                .eq(DeptDO::getName, req.getName()));

        if (count > 0) {
            throw new BusinessException("部门名称已存在");
        }

        DeptDO deptDO = new DeptDO();
        deptDO.setName(req.getName());
        deptDO.setStatus(req.getStatus());
        deptDO.setCreateTime(LocalDateTime.now());
        deptDO.setUpdateTime(LocalDateTime.now());
        return deptMapper.insert(deptDO);
    }

    @Override
    public DeptDO queryDept(Integer id) {
        return deptMapper.selectById(id);
    }

    @Override
    public int updateDept(DeptUpdateReq req) {
        DeptDO deptDO = new DeptDO();
        deptDO.setId(req.getId());
        deptDO.setName(req.getName());
        deptDO.setStatus(req.getStatus());
        deptDO.setUpdateTime(LocalDateTime.now());
        return deptMapper.updateById(deptDO);
    }

    @Override
    public List<DeptDO> listAllDept() {
        return deptMapper.selectList(null);
    }
}
