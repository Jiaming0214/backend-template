package com.ming.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.ming.dto.auth.UserDTO;
import com.ming.entity.auth.User;
import com.ming.mapper.UserMapper;
import com.ming.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends MPJBaseServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByUsernameOrEmail(String usernameOrEmail) {
        return baseMapper.findByUsernameOrEmail(usernameOrEmail);
    }

    @Override
    public Integer createUser(UserDTO userDTO) {
        return baseMapper.createUser(userDTO);
    }

    @Override
    public Integer resetPassword(String password, String email) {
        return baseMapper.resetPassword(password, email);
    }
}
