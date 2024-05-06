package com.xiaozhi.aoaojiao.jwt;


import com.xiaozhi.aoaojiao.core.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xiaozhi
 */
@SpringBootTest
public class TestJWT {

    @Autowired
    private JwtTokenUtil jwtToken;

    @Test
    public void jwtTest() {
        System.out.println(jwtToken.createToken());
    }

}

