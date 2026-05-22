package com.ming.dto.auth;

import com.alibaba.fastjson2.annotation.JSONField;
import com.ming.entity.auth.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    // 主键ID
    private Long id;

    // 名称
    private String name;

    // 显示名称
    private String zhName;

    private List<Menu> menus;

    @JSONField(serialize = false)
    private Integer current;

    @JSONField(serialize = false)
    private Integer size;
}
