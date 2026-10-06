package com.itheima.Controller;

import com.itheima.dto.Result;
import com.itheima.model.req.LoginReq;
import com.itheima.model.resp.LoginResultResp;
import com.itheima.service.LoginService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result<LoginResultResp> login(@RequestBody @Valid LoginReq req) {
        log.info("login");
        return Result.success(loginService.login(req));
    }
}
