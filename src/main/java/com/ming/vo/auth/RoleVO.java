package com.ming.vo.auth;

import com.alibaba.fastjson2.annotation.JSONField;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO {
    private final String NAME_REGEX = "^[A-Z]{1,50}$";

    // 主键ID
    private Long id;

    // 名称
    @Pattern(regexp = NAME_REGEX)
    @Length(min = 1, max = 50)
    private String name;

    // 显示名称
    @Length(min = 1, max = 20)
    private String zhName;

    @JSONField(serialize = false)
    private Integer current;

    @JSONField(serialize = false)
    private Integer size;
}
