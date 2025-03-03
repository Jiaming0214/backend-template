package com.ming.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.ming.entity.auth.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends MPJBaseMapper<Role> {
    List<Role> findByUserId(Long userId);
}
