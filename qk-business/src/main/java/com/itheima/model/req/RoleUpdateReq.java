package com.itheima.model.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleUpdateReq {
    @NotNull(message = "id不能为空")
    private Integer id;

    @NotBlank(message = "name不能为空")
    private String name;
    private String label;
    private String remark;
}
