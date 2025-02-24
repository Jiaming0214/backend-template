package com.ming.interceptor;

import com.ming.convert.UserConvert;
import com.ming.mapper.UserMapper;
import com.ming.vo.auth.UserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用户认证拦截器
 */
@Component
public class AuthorizeInterceptor implements HandlerInterceptor {
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        com.ming.entity.auth.User userInDb = userMapper.findByUsernameOrEmail(user.getUsername());
        UserVO userVO = UserConvert.INSTANCE.entity2vo(userInDb);
        request.getSession().setAttribute("user", userVO);
        return true;
    }
}
