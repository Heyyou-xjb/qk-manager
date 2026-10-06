package com.itheima.service;

import com.itheima.dto.PageResult;
import com.itheima.model.dataobject.DeptDO;
import com.itheima.model.req.DeptAddReq;
import com.itheima.model.req.DeptPageReq;
import com.itheima.model.req.DeptUpdateReq;

import java.util.List;

public interface DeptService {
    PageResult<DeptDO> findDeptsByPage(DeptPageReq req);

    int deleteDept(Integer id);

    int addDept(DeptAddReq req);

    DeptDO queryDept(Integer id);

    int updateDept(DeptUpdateReq req);

    List<DeptDO> listAllDept();
}
