package com.ming.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.base.Strings;
import com.ming.convert.RoleConvert;
import com.ming.dto.auth.RoleDTO;
import com.ming.entity.auth.Role;
import com.ming.mapper.RoleMapper;
import com.ming.service.RoleService;
import com.ming.vo.auth.RoleVO;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends MPJBaseServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public Page<RoleVO> getPage(RoleDTO roleDTO) {
        return baseMapper.selectJoinPage(
                Page.of(roleDTO.getCurrent(), roleDTO.getSize()),
                RoleVO.class,
                getBaseLambdaWrapper(roleDTO)
        );
    }

    @Override
    public Integer edit(RoleDTO roleDTO) {
        return baseMapper.updateById(RoleConvert.INSTANCE.dto2entity(roleDTO));
    }

    @Override
    public Integer add(RoleDTO roleDTO) {
        return baseMapper.insert(RoleConvert.INSTANCE.dto2entity(roleDTO));
    }

    private MPJLambdaWrapper<Role> getBaseLambdaWrapper(RoleDTO roleDTO) {
        return new MPJLambdaWrapper<Role>()
                .selectAll(Role.class)
                .eq(roleDTO.getId() != null, Role::getId, roleDTO.getId())
                .like(!Strings.isNullOrEmpty(roleDTO.getName()), Role::getName, roleDTO.getName())
                .like(!Strings.isNullOrEmpty(roleDTO.getZhName()), Role::getZhName, roleDTO.getZhName());
    }
}
