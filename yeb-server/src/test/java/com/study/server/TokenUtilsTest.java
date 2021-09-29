package com.study.server;

import com.study.server.config.security.JwtTokenUtil;
import com.study.server.pojo.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenUtilsTest {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Test
    public void test1() throws InterruptedException {
//        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        Admin admin = new Admin();
        admin.setName("123");
        admin.setUsername("asd");
        String token = jwtTokenUtil.generateToken(admin);
        System.out.println(token);
        System.out.println(jwtTokenUtil.validateToken(token, admin));
    }
}
