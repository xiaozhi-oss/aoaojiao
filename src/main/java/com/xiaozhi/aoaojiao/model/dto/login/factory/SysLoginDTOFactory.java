package com.xiaozhi.aoaojiao.model.dto.login.factory;

import cn.hutool.core.bean.BeanUtil;
import com.xiaozhi.aoaojiao.core.enums.LoginType;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.model.dto.login.EmailLoginDTO;
import com.xiaozhi.aoaojiao.model.dto.login.PhoneNumberLoginDTO;
import com.xiaozhi.aoaojiao.model.dto.login.SysUserLoginDTO;
import com.xiaozhi.aoaojiao.model.dto.login.UserNamePasswordLoginDTO;

import java.util.Map;
import java.util.Optional;

/**
 * @author xiaozhi
 *
 * 系统登录DTO工厂
 */
public class SysLoginDTOFactory {

    public static SysUserLoginDTO getLoginDto(Map<String, String> parameters) {
        // 做一个匹配
        String type = parameters.get("loginType");
        LoginType loginType = Optional.ofNullable(type)
                .map(LoginType::parseLoginType)
                .orElseThrow(() -> new BusinessException(ResponseStatus.LOGIN_TYPE_ERROR));

        return switch (loginType) {
            case USERNAME_PWD -> BeanUtil.fillBeanWithMap(parameters, new UserNamePasswordLoginDTO(), false);
            case PHONE_NUMBER -> BeanUtil.fillBeanWithMap(parameters, new PhoneNumberLoginDTO(), false);
            case EMAIL -> BeanUtil.fillBeanWithMap(parameters, new EmailLoginDTO(), false);
        };
    }
}
