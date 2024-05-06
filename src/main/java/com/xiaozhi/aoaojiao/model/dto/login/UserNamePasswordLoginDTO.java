package com.xiaozhi.aoaojiao.model.dto.login;

import com.xiaozhi.aoaojiao.core.enums.LoginType;
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
public class UserNamePasswordLoginDTO implements SysUserLoginDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String verificationCode;

    private String uuid;

    @Override
    public LoginType getLoginType() {
        return LoginType.USERNAME_PWD;
    }

    public String getVerificationCode() {
        return this.verificationCode.toLowerCase();
    }
}
