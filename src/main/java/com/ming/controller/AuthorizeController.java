package com.ming.controller;

import com.ming.convert.UserConvert;
import com.ming.entity.RestBean;
import com.ming.service.AuthorizeService;
import com.ming.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户验证相关Controller
 */
@RestController
@RequestMapping("/api/auth")
@Validated
@AllArgsConstructor
public class AuthorizeController {
    private final AuthorizeService authorizeService;

    /**
     * 验证电子邮件正则表达式
     */
    private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";


    /**
     * 验证码发送接口
     */
    @PostMapping("/valid-register-email")
    public RestBean<String> validateRegisterEmail(
            @Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
            HttpSession session) {
        String validateResult = authorizeService.sendValidateEmail(email, session.getId(), false);
        if (validateResult == null) {
            return RestBean.success("邮件已发送，请注意查收");
        } else {
            return RestBean.failure(400, validateResult);
        }
    }

    @PostMapping("/valid-reset-email")
    public RestBean<String> validateResetEmail(
            @Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
            HttpSession session) {
        String validateResult = authorizeService.sendValidateEmail(email, session.getId(), true);
        if (validateResult == null) {
            return RestBean.success("邮件已发送，请注意查收");
        } else {
            return RestBean.failure(400, validateResult);
        }
    }

    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    public RestBean<String> registerUser(
            @RequestBody @Validated UserVO userVO,
            HttpSession session
    ) {
        String result = authorizeService.validateAndRegister(UserConvert.INSTANCE.vo2dto(userVO), session.getId());
        if (result == null) {
            return RestBean.success("注册成功");
        } else {
            return RestBean.failure(400, result);
        }
    }

    @PostMapping("/start-reset")
    public RestBean<String> startReset(
            @RequestBody @Validated UserVO userVO,
            HttpSession session
    ) {
        String result = authorizeService.validateOnly(UserConvert.INSTANCE.vo2dto(userVO), session.getId());
        if (result == null) {
            session.setAttribute("reset-password", userVO.getEmail());
            return RestBean.success("验证成功");
        } else {
            return RestBean.failure(400, result);
        }
    }

    @PostMapping("/reset-password")
    public RestBean<String> resetPassword(
            @RequestBody @Validated UserVO userVO,
            HttpSession session
    ) {
        String email = (String)session.getAttribute("reset-password");
        if(email == null) {
            return RestBean.failure(400, "请先完成邮箱验证");
        }else if(authorizeService.resetPassword(userVO.getPassword(), email)){
            session.removeAttribute("reset-password");
            return RestBean.success("密码修改成功");
        }else {
            return RestBean.failure(500, "系统内部发生错误，请联系管理员");
        }
    }

}
