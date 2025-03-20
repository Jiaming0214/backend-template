package com.ming.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ming.convert.RoleConvert;
import com.ming.dto.auth.RoleDTO;
import com.ming.entity.RestBean;
import com.ming.service.RoleService;
import com.ming.vo.auth.RoleVO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/page")
    public RestBean<Page<RoleVO>> list(RoleVO roleVO) {
        RoleDTO roleDTO = RoleConvert.INSTANCE.vo2dto(roleVO);
        return RestBean.success(roleService.getPage(roleDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @PostMapping
    public RestBean<String> addRole(@RequestBody @Validated RoleVO roleVO) {
        RoleDTO roleDTO = RoleConvert.INSTANCE.vo2dto(roleVO);
        Integer success = roleService.add(roleDTO);
        if (success > 0) {
            return RestBean.success();
        } else {
            return RestBean.failure(400, "添加失败");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public RestBean<RoleVO> getInfo(@PathVariable("id") Long id) {
        return RestBean.success(RoleConvert.INSTANCE.entity2vo(roleService.getById(id)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @PutMapping
    public RestBean<String> update(@RequestBody @Validated RoleVO roleVO) {
        RoleDTO roleDTO = RoleConvert.INSTANCE.vo2dto(roleVO);
        Integer success = roleService.edit(roleDTO);
        if (success > 0) {
            return RestBean.success();
        } else {
            return RestBean.failure(400, "更新失败");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @DeleteMapping("/{id}")
    public RestBean<Void> delete(@PathVariable("id") Long id) {
        roleService.removeById(id);
        return RestBean.success();
    }

}
