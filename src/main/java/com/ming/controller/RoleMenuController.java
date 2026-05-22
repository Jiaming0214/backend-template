package com.ming.controller;

import com.ming.convert.RoleConvert;
import com.ming.dto.auth.RoleDTO;
import com.ming.entity.RestBean;
import com.ming.service.RoleMenuService;
import com.ming.vo.auth.RoleVO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role-menu")
@AllArgsConstructor
public class RoleMenuController {
    private final RoleMenuService roleMenuService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{roleId}")
    public RestBean<RoleVO> getRoleMenuInfo(@PathVariable("roleId") Long roleId) {
        RoleDTO roleDTO = roleMenuService.getRoleMenuInfo(roleId);
        return RestBean.success(RoleConvert.INSTANCE.dto2vo(roleDTO));
    }

}
