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
@TableName("t_user")
public class User {
    // 用户ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 电子邮箱
    private String email;
    // 删除状态
    @TableLogic
    private Integer deleted;
}
