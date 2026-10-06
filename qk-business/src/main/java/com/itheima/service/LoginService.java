package com.itheima.service;

import com.itheima.dto.Result;
import com.itheima.model.req.LoginReq;
import com.itheima.model.resp.LoginResultResp;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginService {

    LoginResultResp login(@RequestBody @Valid LoginReq req);
}
