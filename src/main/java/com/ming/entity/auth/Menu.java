package com.ming.entity.auth;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_menu")
public class Menu {
    // 主键ID
    @TableId(value = "id", type = IdType.AUTO)
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

    // 菜单是否删除
    // 0：未删除
    // 1：已删除
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private List<Menu> children;
}
