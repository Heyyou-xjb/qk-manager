package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.model.dataobject.BusinessTrackRecordDO;
import com.itheima.model.resp.BusinessTrackRecordResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 商机跟进记录表 Mapper 接口
 * </p>
 *
 * @author niubi
 * @since 2026-09-30
 */
@Mapper
public interface BusinessTrackRecordMapper extends BaseMapper<BusinessTrackRecordDO> {

    List<BusinessTrackRecordResp> queryListByBusinessId(Integer businessId);

}
