package com.ming.mapper;

import com.ming.dto.UserDTO;
import com.ming.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByUsernameOrEmail(String text);

    Integer createUser(UserDTO userDTO);

    Integer resetPassword(@Param("password") String password, @Param("email") String email);
}
