package com.ming.service;

import com.github.yulichang.base.MPJBaseService;
import com.ming.dto.auth.UserDTO;
import com.ming.entity.auth.User;

public interface UserService extends MPJBaseService<User> {
    User getByUsernameOrEmail(String usernameOrEmail);

    Integer createUser(UserDTO userDTO);

    Integer resetPassword(String password, String email);
}
