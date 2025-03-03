package com.ming.convert;

import com.ming.dto.auth.MenuDTO;
import com.ming.entity.auth.Menu;
import com.ming.vo.auth.MenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuConvert {
    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    MenuDTO vo2dto(MenuVO menuVO);

    MenuDTO entity2dto(Menu menu);

    MenuVO dto2vo(MenuDTO menuDTO);

    MenuVO entity2vo(Menu menu);

    Menu dto2entity(MenuDTO menuDTO);

    List<MenuDTO> entityList2dtoList(List<Menu> menuList);

    List<MenuVO> entityList2voList(List<Menu> menuList);

    List<MenuVO> dtoList2voList(List<MenuDTO> menuDTOList);
}
