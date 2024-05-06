package com.xiaozhi.aoaojiao.service;

import com.xiaozhi.aoaojiao.model.vo.login.CaptchaImgVo;

/**
 * @author xiaozhi
 *
 * 提供各种验证码
 */
public interface SysCodeService {

    /**
     * 发送验证码图片
     */
    CaptchaImgVo captchaCodeImg();

    /**
     * 发送邮箱验证码
     * @param receiverEmail 接收者邮箱
     */
    void sendEmailCode(String receiverEmail);

    /**
     * 发送手机验证码
     * @param phoneNumber   手机号
     */
    void sendMobileCode(String phoneNumber);
}
