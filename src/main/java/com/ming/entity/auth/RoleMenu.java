package com.ming.entity.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role_menu")
public class RoleMenu {
    // 主键ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 角色ID
    private Long rid;

    // 菜单ID
    private Long mid;
}
