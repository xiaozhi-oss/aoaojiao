package com.xiaozhi.aoaojiao.email;

import cn.hutool.core.util.RandomUtil;
import com.xiaozhi.aoaojiao.core.utils.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xiaozhi
 *
 * 邮箱发送测试
 */
@SpringBootTest
public class EmailTest {

    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String to;

    @Test
    public void test() {
        emailService.send(RandomUtil.randomNumbers(6), to);
    }
}
