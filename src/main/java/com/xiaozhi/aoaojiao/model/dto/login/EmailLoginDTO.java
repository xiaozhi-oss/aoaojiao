package com.xiaozhi.aoaojiao.model.dto.login;

import com.xiaozhi.aoaojiao.core.enums.LoginType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaozhi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailLoginDTO implements SysUserLoginDTO {

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不对")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    @Override
    public LoginType getLoginType() {
        return LoginType.EMAIL;
    }
}
