package com.ming.dto.auth;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    // 用户ID
    private Long id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 电子邮箱
    private String email;
    // 删除状态
    private Integer deleted;
    // 验证码
    private String validateCode;

    // 角色信息
    private List<RoleDTO> roles;

    @JSONField(serialize = false)
    private Integer current;

    @JSONField(serialize = false)
    private Integer size;
}
