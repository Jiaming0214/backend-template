package com.ming.controller;

import com.ming.entity.RestBean;
import com.ming.vo.UserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public RestBean<UserVO> me(@SessionAttribute("user") UserVO user){
        return RestBean.success(user);
    }

}
