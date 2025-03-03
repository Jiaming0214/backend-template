package com.ming.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.ming.dto.auth.UserDTO;
import com.ming.entity.auth.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends MPJBaseMapper<User> {
    User findByUsernameOrEmail(String text);

    Integer createUser(UserDTO userDTO);

    Integer resetPassword(@Param("password") String password, @Param("email") String email);
}
