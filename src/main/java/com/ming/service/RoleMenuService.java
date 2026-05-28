package com.ming.service;

import com.github.yulichang.base.MPJBaseService;
import com.ming.dto.auth.MenuDTO;
import com.ming.dto.auth.RoleDTO;
import com.ming.entity.auth.RoleMenu;
import com.ming.vo.auth.RoleVO;

import java.util.List;

public interface RoleMenuService extends MPJBaseService<RoleMenu> {
    RoleDTO getRoleMenuInfo(Long roleId);

    List<MenuDTO> getMenusByRoleIds(List<Long> roleIds);
}
