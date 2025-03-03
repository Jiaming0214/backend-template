package com.ming.convert;

import com.ming.dto.auth.RoleDTO;
import com.ming.entity.auth.Role;
import com.ming.vo.auth.RoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleConvert {
    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    RoleDTO vo2dto(RoleVO roleVO);

    RoleDTO entity2dto(Role role);

    RoleVO dto2vo(RoleDTO roleDTO);

    RoleVO entity2vo(Role role);

    Role dto2entity(RoleDTO roleDTO);

    List<RoleVO> entityList2voList(List<Role> roleList);
}
