package com.ming.controller;

import com.ming.entity.RestBean;
import com.ming.service.AuthorizeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户验证相关Controller
 * */
@RestController
@RequestMapping("/api/auth")
@Validated
@AllArgsConstructor
public class AuthorizeController {
    private final AuthorizeService authorizeService;

    /** 验证电子邮件正则表达式 */
    private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @PostMapping("/valid-email")
    public RestBean<String> validateEmail(
            @Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
            HttpSession session) {
        if(authorizeService.sendValidateEmail(email, session.getId())) {
            return RestBean.success("邮件已发送，请注意查收");
        }else {
            return RestBean.failure(400,"邮件发送失败，请联系管理员");
        }
    }

}
