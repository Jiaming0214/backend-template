package com.ming.mapper;

import com.ming.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUsernameOrEmail(String text);
}
