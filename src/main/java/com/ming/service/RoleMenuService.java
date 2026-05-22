package com.ming.service;

import com.github.yulichang.base.MPJBaseService;
import com.ming.dto.auth.RoleDTO;
import com.ming.entity.auth.RoleMenu;
import com.ming.vo.auth.RoleVO;

public interface RoleMenuService extends MPJBaseService<RoleMenu> {
    RoleDTO getRoleMenuInfo(Long roleId);
}
