package com.ming.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    /**
     * 验证用户名正则表达式
     */
    private final String USERNAME_REGEX = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 验证电子邮件正则表达式
     */
    private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @NotNull
    @Pattern(regexp = USERNAME_REGEX)
    @Length(min = 1, max = 20)
    private String username;

    @NotNull
    @Length(min = 6, max = 20)
    private String password;

    @NotNull
    @Pattern(regexp = EMAIL_REGEX)
    private String email;

    @NotNull
    private String validateCode;
}
