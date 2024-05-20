package com.xiaozhi.aoaojiao.service.serviceImpl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.xiaozhi.aoaojiao.core.constants.RedisConstants;
import com.xiaozhi.aoaojiao.core.constants.TimeConstants;
import com.xiaozhi.aoaojiao.core.enums.ResponseStatus;
import com.xiaozhi.aoaojiao.core.exception.BusinessException;
import com.xiaozhi.aoaojiao.core.utils.EmailService;
import com.xiaozhi.aoaojiao.core.utils.RedisUtil;
import com.xiaozhi.aoaojiao.model.vo.CaptchaImgVO;
import com.xiaozhi.aoaojiao.service.SysCodeService;
import com.xiaozhi.aoaojiao.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author xiaozhi
 */
@Service
public class SysCodeServiceImpl implements SysCodeService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public CaptchaImgVO captchaCodeImg() {
        // 生成 uuid
        String uuid = IdUtil.fastUUID();
        // 使用的是 hutool 中的验证码
        var captcha = CaptchaUtil.createShearCaptcha(130, 40, 4, 4);
        String code = captcha.getCode().toLowerCase();
        // 存入 redis
        redisUtil.set(RedisConstants.getLoginCodeKey(uuid), code, TimeConstants.IMG_CAPTCHA_EXPIRE_TIME);
        String imageBase64Data = captcha.getImageBase64Data();
        return new CaptchaImgVO(uuid, imageBase64Data);
    }

    @Async("asyncExecutor")
    @Override
    public void sendEmailCode(String receiverEmail) {
        try {
            String code = RandomUtil.randomNumbers(6);
            emailService.sendCode(code, receiverEmail);
            redisUtil.set(RedisConstants.getLoginCodeKey(receiverEmail), code, TimeConstants.CODE_EXPIRE_TIME);
        } catch (MailSendException e) {
            throw new BusinessException(ResponseStatus.EMAIL_SEND_ERROR);
        }
    }


    @Override
    public void sendMobileCode(String phoneNumber) {

    }
}
