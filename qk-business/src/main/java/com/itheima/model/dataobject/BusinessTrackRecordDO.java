package com.itheima.model.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商机跟进记录表
 * </p>
 *
 * @author niubi
 * @since 2026-09-30
 */
@Getter
@Setter
@ToString
@TableName("business_track_record")
public class BusinessTrackRecordDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 跟进记录id, 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商机id，关联商机id
     */
    private Integer businessId;

    /**
     * 跟进人id，关联用户id
     */
    private Integer userId;

    /**
     * 跟进状态, 1:接通, 2:拒绝, 3:无人接听
     */
    private Integer trackStatus;

    /**
     * 沟通重点
     */
    private String keyItems;

    /**
     * 下次跟进时间
     */
    private LocalDateTime nextTime;

    /**
     * 沟通纪要
     */
    private String record;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
