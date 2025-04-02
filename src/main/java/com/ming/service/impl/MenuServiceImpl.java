package com.ming.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.ming.convert.MenuConvert;
import com.ming.dto.auth.MenuDTO;
import com.ming.entity.auth.Menu;
import com.ming.exception.ServiceException;
import com.ming.mapper.MenuMapper;
import com.ming.service.MenuService;
import com.ming.vo.auth.MenuVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
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
    public MenuVO getInfo(Long id) {
        Menu menu = baseMapper.selectById(id);
        List<Menu> children = baseMapper.selectList(
                new MPJLambdaWrapper<Menu>()
                        .eq(Menu::getParentId, id)
        );
        menu.setChildren(children);
        return MenuConvert.INSTANCE.entity2vo(menu);
    }

    @Override
    public Integer delete(Long id) {
        // 查询数据库，确认删除菜单信息
        Menu menu = baseMapper.selectById(id);
        if (menu == null) {
            throw new ServiceException(500, "菜单不存在");
        }

        // 如果不是三级菜单，那么需要获取该菜单的子菜单，并删除子菜单
        if (menu.getLevel() == 1) { // 一级菜单处理
            // 获取二级菜单
            List<Menu> secondMenuList = baseMapper.selectList(
                    new MPJLambdaWrapper<Menu>()
                            .eq(Menu::getParentId, menu.getId())
            );
            // 如果二级菜单不为空，那么需要删除二级菜单和与其相关的三级菜单信息
            if (!CollectionUtils.isEmpty(secondMenuList)) {
                // 获取二级菜单的id
                List<Long> secondMenuIds = secondMenuList.stream()
                        .map(Menu::getId)
                        .toList();
                // 删除二级菜单和与其相关的三级菜单信息
                baseMapper.delete(
                        new MPJLambdaWrapper<Menu>()
                                .in(Menu::getParentId, secondMenuIds)
                );
                // 删除二级菜单信息
                baseMapper.deleteByIds(secondMenuIds);
            }
        } else if (menu.getLevel() == 2) { // 二级菜单处理
            // 获取三级菜单
            List<Menu> thirdMenuList = baseMapper.selectList(
                    new MPJLambdaWrapper<Menu>()
                            .eq(Menu::getParentId, menu.getId())
            );
            // 如果三级菜单不为空，那么需要删除三级菜单信息
            if (!CollectionUtils.isEmpty(thirdMenuList)) {
                List<Long> thirdMenuIds = thirdMenuList.stream()
                        .map(Menu::getId)
                        .toList();
                baseMapper.deleteByIds(thirdMenuIds);
            }
        }
        return baseMapper.deleteById(menu.getId());
    }

    @Override
    public Integer edit(MenuDTO menuDTO) {
        if (menuDTO.getLevel() > 1 && menuDTO.getParentId() == null) {
            throw new ServiceException(500, "二级以及三级菜单必须指定父级菜单");
        }
        if (menuDTO.getLevel() == 3 && Strings.isNullOrEmpty(menuDTO.getComponentPath())) {
            throw new ServiceException(500, "三级菜单必须指定组件路径");
        }
        return baseMapper.updateById(MenuConvert.INSTANCE.dto2entity(menuDTO));
    }

    @Override
    public Integer add(MenuDTO menuDTO) {
        if (menuDTO.getLevel() > 1 && menuDTO.getParentId() == null) {
            throw new ServiceException(500, "二级以及三级菜单必须指定父级菜单");
        }
        if (menuDTO.getLevel() == 3 && Strings.isNullOrEmpty(menuDTO.getComponentPath())) {
            throw new ServiceException(500, "三级菜单必须指定组件路径");
        }
        return baseMapper.insert(MenuConvert.INSTANCE.dto2entity(menuDTO));
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
                            .sorted(Comparator.comparing(Menu::getOrderNum))
                            .collect(Collectors.toList())
            );
        });
        // 设置一级菜单子菜单信息
        rootMenuList.forEach(rootMenu -> {
            rootMenu.setChildren(
                    secondMenuList.stream()
                            .filter(secondMenu -> secondMenu.getParentId().equals(rootMenu.getId()))
                            .sorted(Comparator.comparing(Menu::getOrderNum))
                            .collect(Collectors.toList())
            );
        });
        return rootMenuList.stream().sorted(Comparator.comparing(Menu::getOrderNum)).toList();
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
