package com.itheima.interceptor;

import cn.hutool.core.util.StrUtil;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.UserHolder;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//自定义拦截器
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    //JwtUtil 是 Spring 管理的 Bean，必须注入使用；直接 new 出来的对象没有注入 @Value 配置，解析令牌必然失败
    @Autowired
    private JwtUtil jwtUtil;

    //目标资源方法执行前执行。 返回true：放行    返回false：不放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("preHandle");

        // 获取token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            // 重定向登录页面 401
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }

        // 校验token合法性
        try {
            Claims claims = jwtUtil.parseToken(token);
            Integer id = claims.get("id", Integer.class);
            UserHolder.setUserId(id);
            log.info("claims: {}", claims);
        } catch (Exception e) {
            log.warn("token校验失败: {}", e.getMessage());
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }
        log.info("token校验通过");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("afterCompletion");
        UserHolder.removeUserId();
    }
}
