package com.xiaozhi.aoaojiao.code;

import com.xiaozhi.aoaojiao.model.vo.login.CaptchaImgVo;
import com.xiaozhi.aoaojiao.service.SysCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xiaozhi
 */
@SpringBootTest
public class CodeServiceTest {

    @Autowired
    private SysCodeService sysCodeService;

    @Test
    public void test01(){
        CaptchaImgVo captchaImgVo = sysCodeService.captchaCodeImg();
        System.out.println(captchaImgVo);
    }
}
