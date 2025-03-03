package com.ming.controller;

import com.ming.convert.MenuConvert;
import com.ming.dto.auth.MenuDTO;
import com.ming.entity.RestBean;
import com.ming.service.MenuService;
import com.ming.vo.auth.MenuVO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@AllArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public RestBean<List<MenuVO>> list(MenuVO menuVO) {
        MenuDTO dto = MenuConvert.INSTANCE.vo2dto(menuVO);
        List<MenuDTO> menuDTOList = menuService.getList(dto);
        List<MenuVO> menuVOList = MenuConvert.INSTANCE.dtoList2voList(menuDTOList);
        return RestBean.success(menuVOList);
    }

}
