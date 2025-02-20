package com.ming.mapper;

import com.ming.dto.UserDTO;
import com.ming.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUsernameOrEmail(String text);

    Integer createUser(UserDTO userDTO);
}
