package com.xiaozhi.aoaojiao.model.dto.login;

import com.xiaozhi.aoaojiao.core.enums.LoginType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaozhi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberLoginDTO implements SysUserLoginDTO {

    /**
     * 手机号
     */
    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$/\n",
            message = "电话号码格式不对")
    private String phoneNumber;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    @Override
    public LoginType getLoginType() {
        return LoginType.PHONE_NUMBER;
    }
}
