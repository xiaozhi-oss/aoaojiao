package com.xiaozhi.aoaojiao.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author xiaozhi
 */
@Component
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 发送验证码
     * @param code  验证码
     */
    public void sendCode(String code, String sendTo) {
        String text = String.format("【%s】您的验证码是 %s。请妥善保管。", applicationName, code);
        send(text, sendTo);
    }

    /**
     * 发送简单邮件
     * @param content   发送内容
     * @param sendTo    接收者
     */
    public void send(String content, String sendTo) {
        log.info("发送邮箱验证码 TO： {}", sendTo);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setText(content);
        message.setTo(sendTo);
        message.setSubject("验证码");
        try {
            mailSender.send(message);
        } catch (Exception e) {
            log.error("邮箱验证码发送失败...");
            throw new MailSendException("邮箱验证码发送失败");
        }
        log.info("【{}】发送邮箱验证码成功", sendTo);
    }
}
