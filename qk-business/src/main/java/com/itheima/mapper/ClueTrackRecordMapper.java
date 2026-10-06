package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.model.dataobject.ClueTrackRecordDO;
import com.itheima.model.resp.ClueTrackRecordResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 线索跟进记录表 Mapper 接口
 * </p>
 *
 * @author niubi
 * @since 2026-09-29
 */
@Mapper
public interface ClueTrackRecordMapper extends BaseMapper<ClueTrackRecordDO> {

    List<ClueTrackRecordResp> queryListByClueId(Integer clueId);

}
