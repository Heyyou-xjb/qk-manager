package com.itheima.model.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeptUpdateReq {

    @NotNull(message = "id不能为空")
    private Integer id;

    @NotNull(message = "name不能为空")
    private String name;
    private Integer status;
}
