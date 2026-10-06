package com.itheima.model.resp;

import lombok.Data;

@Data
public class LoginResultResp {
    private Integer id;
    private String username;
    private String name;
    private String image;
    private String roleLabel;
    private String token;

}
