package com.ming.backendtemplate;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class BackendTemplateApplicationTests {

    @Resource
    StringRedisTemplate template;

    @Test
    void contextLoads() {
        template.opsForValue().set("springbootTest", "ok");
        System.out.println(template.opsForValue().get("springbootTest"));
    }

}
