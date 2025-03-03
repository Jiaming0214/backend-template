package com.ming.interceptor;

import com.google.common.collect.Lists;
import com.ming.convert.MenuConvert;
import com.ming.convert.RoleConvert;
import com.ming.convert.UserConvert;
import com.ming.dto.auth.MenuDTO;
import com.ming.entity.auth.Role;
import com.ming.exception.ServiceException;
import com.ming.mapper.RoleMapper;
import com.ming.mapper.UserMapper;
import com.ming.service.MenuService;
import com.ming.vo.auth.MenuVO;
import com.ming.vo.auth.RoleVO;
import com.ming.vo.auth.UserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

/**
 * 用户认证拦截器
 */
@Component
public class AuthorizeInterceptor implements HandlerInterceptor {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuService menuService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 通过上下文获取用户信息
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();

        if(user == null) throw new ServiceException(401, "用户未登录");

        com.ming.entity.auth.User userInDb = userMapper.findByUsernameOrEmail(user.getUsername());
        if(userInDb == null) throw new ServiceException(401, "用户不存在");

        // 获取用户角色信息
        List<Role> roles = roleMapper.findByUserId(userInDb.getId());
        if(CollectionUtils.isEmpty(roles)) throw new ServiceException(500, "系统未知错误，请联系管理员");
        List<RoleVO> roleVOList = RoleConvert.INSTANCE.entityList2voList(roles);

        List<MenuVO> menuVOList = Lists.newArrayList();
        if(roles.stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
            List<MenuDTO> menuDTOList = menuService.getAllMenu();
            menuVOList = MenuConvert.INSTANCE.dtoList2voList(menuDTOList);
        }

        UserVO userVO = UserConvert.INSTANCE.buildUserVO(userInDb, roleVOList, menuVOList);
        request.getSession().setAttribute("user", userVO);
        return true;
    }
}
