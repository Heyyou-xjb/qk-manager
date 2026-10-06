package com.itheima.model.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleAddReq {
    @NotBlank(message = "角色名不能为空")
    private String name;
    @NotBlank(message = "角色标签不能为空")
    private String label;
    private String remark;
}
