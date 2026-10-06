package com.itheima.model.req;

import lombok.Data;

@Data
public class ClueFalseReq {
    /**
     * 伪线索原因, 1:空号、2:停机、3:竞品、4:无法联系、5:其他
     */
    private Integer reason;
    /**
     * 备注信息
     */
    private String remark;
}