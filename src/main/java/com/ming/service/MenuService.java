package com.ming.service;

import com.github.yulichang.base.MPJBaseService;
import com.ming.dto.auth.MenuDTO;
import com.ming.entity.auth.Menu;

import java.util.List;

public interface MenuService extends MPJBaseService<Menu> {
    List<MenuDTO> getList(MenuDTO dto);

    List<MenuDTO> getAllMenu();
}
