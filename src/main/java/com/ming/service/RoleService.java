package com.ming.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseService;
import com.ming.dto.auth.RoleDTO;
import com.ming.entity.auth.Role;
import com.ming.vo.auth.RoleVO;

public interface RoleService extends MPJBaseService<Role> {
    /**
     * 分页查询角色信息
     */
    Page<RoleVO> getPage(RoleDTO roleDTO);

    /**
     * 修改角色信息
     */
    Integer edit(RoleDTO roleDTO);

    /**
     * 添加角色信息
     */
    Integer add(RoleDTO roleDTO);
}
