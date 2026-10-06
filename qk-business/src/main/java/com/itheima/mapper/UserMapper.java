package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.model.dataobject.UserDO;
import com.itheima.model.req.UserPageReq;
import com.itheima.model.resp.UserResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author niubi
 * @since 2026-09-26
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
    Page<UserResp> queryPageList(Page<UserResp> page, UserPageReq req);

    List<UserDO> queryListByRoleLabel(String roleLabel);

    List<UserDO> queryListByDeptId(Integer deptId);
}
