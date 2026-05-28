package com.ming.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.ming.convert.RoleConvert;
import com.ming.dto.auth.RoleDTO;
import com.ming.entity.auth.Menu;
import com.ming.entity.auth.Role;
import com.ming.entity.auth.RoleMenu;
import com.ming.exception.ServiceException;
import com.ming.mapper.RoleMenuMapper;
import com.ming.service.MenuService;
import com.ming.service.RoleMenuService;
import com.ming.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleMenuServiceImpl extends MPJBaseServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    private final RoleService roleSerivice;
    private final MenuService menuService;

    @Override
    public RoleDTO getRoleMenuInfo(Long roleId) {
        // 先查询角色信息
        Role role = Optional.ofNullable(roleSerivice.getById(roleId))
                .orElseThrow(() -> new ServiceException(500, "角色不存在"));
        // 查询角色对应的菜单信息
        List<RoleMenu> roleMenus = baseMapper.selectList(
                new MPJLambdaWrapper<RoleMenu>()
                        .eq(RoleMenu::getRid, role.getId())
        );
        // 如果角色对应的菜单信息不为空，那么需要查询菜单信息
        if(!CollectionUtils.isEmpty(roleMenus)) {
            List<Long> menuIds = roleMenus.stream()
                    .map(RoleMenu::getMid)
                    .toList();
            List<Menu> menus = menuService.list(
                    new MPJLambdaWrapper<Menu>()
                            .in(Menu::getId, menuIds)
            );
            return RoleConvert.INSTANCE.buildRoleDTO(role, menus);
        } else {
            return RoleConvert.INSTANCE.entity2dto(role);
        }
    }
}
