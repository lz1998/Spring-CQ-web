package net.lz1998.demo.security;

import lombok.extern.slf4j.Slf4j;
import net.lz1998.demo.SpringCqDemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@SpringBootTest(classes = SpringCqDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtUtilTest {
    @Autowired
    JwtUtil jwtUtil;

    @Test
    void createJWT() {
        String jwt = jwtUtil.createJWT(false, 12345678L);
        log.info(jwt);
    }

    @Test
    void parseJWT() {

    }

    @Test
    void getUserIdFromJWT() {
        long userId = jwtUtil.getUserIdFromJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NTY3OCIsInN1YiI6IjEyMzQ1Njc4IiwiaWF0IjoxNTkzMDg4MzI2LCJleHAiOjE1OTMwODg5MjZ9.-t5zLvSX1r_lIDGcK9xCYcvuibC07FfBg8bCViNlPdg");
        log.info("{}",userId);
    }
}