package com.ming.entity.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role")
public class Role {
    // 主键ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 名称
    private String name;

    // 显示名称
    private String zhName;

    // 删除状态
    @TableLogic
    private Integer deleted;
}
