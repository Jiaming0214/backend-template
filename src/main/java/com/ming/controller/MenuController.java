package com.ming.controller;

import com.ming.convert.MenuConvert;
import com.ming.dto.auth.MenuDTO;
import com.ming.entity.RestBean;
import com.ming.service.MenuService;
import com.ming.vo.auth.MenuVO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public RestBean<MenuVO> getInfo(@PathVariable("id") Long id) {
        MenuVO menuVO = menuService.getInfo(id);
        return RestBean.success(menuVO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public RestBean<String> add(@RequestBody @Validated MenuVO menuVO) {
        Integer success = menuService.add(MenuConvert.INSTANCE.vo2dto(menuVO));
        if(success > 0) {
            return RestBean.success("添加成功");
        } else {
            return RestBean.failure(500, "添加失败");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @DeleteMapping("/{id}")
    public RestBean<String> delete(@PathVariable("id") Long id) {
        Integer success = menuService.delete(id);
        if(success > 0) {
            return RestBean.success("删除成功");
        } else {
            return RestBean.failure(500, "删除失败");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public RestBean<String> update(@RequestBody @Validated MenuVO menuVO) {
        Integer success = menuService.edit(MenuConvert.INSTANCE.vo2dto(menuVO));
        if(success > 0) {
            return RestBean.success("更新成功");
        } else {
            return RestBean.failure(500, "更新失败");
        }
    }

}
