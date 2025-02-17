package com.ming.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    // 用户ID
    private Long id;
    // 用户昵称
    private String nickname;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 电子邮箱
    private String email;
    // 删除状态
    private Integer deleted;
}
