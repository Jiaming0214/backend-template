package com.ming.backendtemplate;

import com.ming.convert.UserConvert;
import com.ming.dto.auth.UserDTO;
import com.ming.entity.auth.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendTemplateApplicationTests {

    @Test
    void contextLoads() {
        User user = new User(1L, "ming", "123456", "ming@qq.com", 0);
        UserDTO userDTO = UserConvert.INSTANCE.entity2dto(user);
        System.out.println(userDTO);
    }

}
