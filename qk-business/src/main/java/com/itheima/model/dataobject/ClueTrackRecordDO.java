package com.itheima.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 线索跟进记录表
 * </p>
 *
 * @author niubi
 * @since 2026-09-29
 */
@Getter
@Setter
@ToString
@TableName("clue_track_record")
public class ClueTrackRecordDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 跟进记录ID, 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 线索ID，关联线索ID
     */
    private Integer clueId;

    /**
     * 跟进人ID，关联用户ID
     */
    private Integer userId;

    /**
     * 意向学科，1:AI智能应用开发(Java), 2:AI大模型开发(Python)，3:AI鸿蒙开发，4:AI大数据，5:AI嵌入式，6:AI测试，7:AI运维
     */
    private Integer subject;

    /**
     * 意向等级, 1:近期学习、2:打算学习(考虑中)、3:进行了解、4:打酱油
     */
    private Integer level;

    /**
     * 跟进记录
     */
    private String record;

    /**
     * 下次跟进时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime nextTime;

    /**
     * 跟进类型, 1:正常跟进、0:伪线索
     */
    private Integer type;

    /**
     * 伪线索原因, 1:空号、2:停机、3:竞品、4:无法联系、5:其他
     */
    private Integer falseReason;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
