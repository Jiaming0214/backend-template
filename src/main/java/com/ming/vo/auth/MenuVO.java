package com.ming.vo.auth;

import com.alibaba.fastjson2.annotation.JSONField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MenuVO {
    // 主键ID
    private Long id;

    // 菜单名称
    @NotNull(message = "菜单名称不能为空")
    @NotBlank(message = "菜单名称不能为空")
    @Length(min = 1, max = 20, message = "菜单名称长度为1-20位")
    private String name;

    // 菜单中文名称
    @NotNull(message = "菜单中文名称不能为空")
    @NotBlank(message = "菜单中文名称不能为空")
    private String zhName;

    // 菜单前端路由
    @NotNull(message = "菜单前端路由不能为空")
    @NotBlank(message = "菜单前端路由不能为空")
    private String path;

    // 菜单组件路径
    private String componentPath;

    // 菜单上级ID
    private Long parentId;

    // 菜单层级
    @NotNull(message = "菜单层级不能为空")
    private Integer level;

    // 菜单备注
    private String remark;

    // 子菜单
    private List<MenuVO> children;

    @JSONField(serialize = false)
    private Integer current;

    @JSONField(serialize = false)
    private Integer size;
}
