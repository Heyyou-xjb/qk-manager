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
 * 操作日志表
 * </p>
 *
 * @author niubi
 * @since 2026-09-30
 */
@Getter
@Setter
@ToString
@TableName("operate_log")
public class OperateLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作用户ID
     */
    private Integer operateUserId;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

    /**
     * 操作的类名
     */
    private String className;

    /**
     * 操作的方法名
     */
    private String methodName;

    /**
     * 方法参数
     */
    private String methodParams;

    /**
     * 返回值
     */
    private String returnValue;

    /**
     * 方法执行耗时, 单位:ms
     */
    private Long costTime;
}
