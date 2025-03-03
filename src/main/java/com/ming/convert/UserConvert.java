package com.ming.convert;

import com.ming.dto.auth.UserDTO;
import com.ming.entity.auth.Role;
import com.ming.entity.auth.User;
import com.ming.vo.auth.MenuVO;
import com.ming.vo.auth.RoleVO;
import com.ming.vo.auth.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper // 这里注意一下，使用的是MapStruct，而不是MyBatis的Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserDTO vo2dto(UserVO userVO);

    UserDTO entity2dto(User user);

    UserVO dto2vo(UserDTO userDTO);

    UserVO entity2vo(User user);

    User dto2entity(UserDTO userDTO);

    UserVO buildUserVO(User user, List<RoleVO> roles, List<MenuVO> menus);
}
