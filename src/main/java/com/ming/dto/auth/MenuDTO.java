package com.ming.dto.auth;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    // 主键ID
    private Long id;

    // 菜单名称
    private String name;

    // 菜单中文名称
    private String zhName;

    // 菜单前端路由
    private String path;

    // 菜单组件路径
    private String componentPath;

    // 菜单上级ID
    private Long parentId;

    // 菜单层级
    private Integer level;

    // 菜单备注
    private String remark;

    private List<MenuDTO> children;

    @JSONField(serialize = false)
    private Integer current;

    @JSONField(serialize = false)
    private Integer size;
}
