package com.xiaozhi.aoaojiao.controller;

import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import com.xiaozhi.aoaojiao.model.vo.CaptchaImgVO;
import com.xiaozhi.aoaojiao.service.SysCodeService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaozhi
 */
@Tag(name = "验证码接口")
@RestController
@RequestMapping("/api/code")
public class SysCodeController {

    @Autowired
    private SysCodeService sysCodeService;

    @GetMapping("/captchaImg")
    public ResponseResult<CaptchaImgVO> getCaptchaImg() {
        return ResponseResult.success(sysCodeService.captchaCodeImg());
    }

    @Parameter(name = "receiverEmail", description = "发送者邮箱", in = ParameterIn.PATH)
    @GetMapping("/emailCode/{email}")
    public ResponseResult<Void> getEmailCode(@PathVariable String email) {
        sysCodeService.sendEmailCode(email);
        return ResponseResult.success();
    }

}
