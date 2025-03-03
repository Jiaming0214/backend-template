package com.ming.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.ming.convert.MenuConvert;
import com.ming.dto.auth.MenuDTO;
import com.ming.entity.auth.Menu;
import com.ming.mapper.MenuMapper;
import com.ming.service.MenuService;
import com.ming.vo.auth.MenuVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends MPJBaseServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<MenuDTO> getList(MenuDTO dto) {
        MPJLambdaWrapper<Menu> wrapper = getBaseLambdaWrapper(dto);
        List<Menu> menuList = baseMapper.selectList(wrapper);
        List<Menu> menuTree = buildTree(menuList);
        return MenuConvert.INSTANCE.entityList2dtoList(menuTree);
    }

    @Override
    public List<MenuDTO> getAllMenu() {
        MPJLambdaWrapper<Menu> wrapper = getBaseLambdaWrapper(new MenuDTO());
        List<Menu> menuList = baseMapper.selectList(wrapper);
        List<Menu> menuTree = buildTree(menuList);
        return MenuConvert.INSTANCE.entityList2dtoList(menuTree);
    }

    private List<Menu> buildTree(List<Menu> menuList) {
        // 提取出一级菜单
        List<Menu> rootMenuList = Lists.newArrayList(Collections2.filter(menuList, menu -> menu.getLevel() == 1));
        // 提取出二级菜单
        List<Menu> secondMenuList = Lists.newArrayList(Collections2.filter(menuList, menu -> menu.getLevel() == 2));
        // 提取出三级菜单
        List<Menu> thirdMenuList = Lists.newArrayList(Collections2.filter(menuList, menu -> menu.getLevel() == 3));
        // 设置二级菜单子菜单信息
        secondMenuList.forEach(secondMenu -> {
            secondMenu.setChildren(
                    thirdMenuList.stream()
                            .filter(thirdMenu -> thirdMenu.getParentId().equals(secondMenu.getId()))
                            .collect(Collectors.toList())
            );
        });
        // 设置一级菜单子菜单信息
        rootMenuList.forEach(rootMenu -> {
            rootMenu.setChildren(
                    secondMenuList.stream()
                            .filter(secondMenu -> secondMenu.getParentId().equals(rootMenu.getId()))
                            .collect(Collectors.toList())
            );
        });
        return rootMenuList;
    }

    private MPJLambdaWrapper<Menu> getBaseLambdaWrapper(MenuDTO dto) {
        return new MPJLambdaWrapper<Menu>()
                .eq(dto.getId() != null, Menu::getId, dto.getId())
                .eq(dto.getLevel() != null, Menu::getLevel, dto.getLevel())
                .eq(dto.getParentId() != null, Menu::getParentId, dto.getParentId())
                .like(!Strings.isNullOrEmpty(dto.getPath()), Menu::getPath, dto.getPath())
                .like(!Strings.isNullOrEmpty(dto.getName()), Menu::getName, dto.getName())
                .like(!Strings.isNullOrEmpty(dto.getZhName()), Menu::getZhName, dto.getZhName());
    }

}
