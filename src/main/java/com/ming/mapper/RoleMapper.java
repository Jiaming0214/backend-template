package com.ming.mapper;

import com.ming.entity.auth.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> findByUserId(Long userId);
}
