package com.ming.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    // 主键ID
    private Long id;

    // 名称
    private String name;

    // 显示名称
    private String zhName;

    // 删除状态
    private Integer deleted;
}
