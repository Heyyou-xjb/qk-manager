package com.itheima;

import com.itheima.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    //生成令牌
    @Test
    public void testGenerateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        String token = jwtUtil.generateToken(claims);
        System.out.println(token);
    }

    //校验令牌（令牌有有效期，这里先生成再解析，避免写死的令牌过期导致测试失败）
    @Test
    public void testValidateToken() {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", 1);
        String token = jwtUtil.generateToken(claimsMap);
        Claims claims = jwtUtil.parseToken(token);
        System.out.println(claims);
    }
}
