package com.xiaozhi.aoaojiao.controller;

import com.xiaozhi.aoaojiao.core.constants.RedisConstants;
import com.xiaozhi.aoaojiao.core.exception.ParameterVerificationException;
import com.xiaozhi.aoaojiao.core.utils.JwtTokenUtil;
import com.xiaozhi.aoaojiao.core.utils.RedisUtil;
import com.xiaozhi.aoaojiao.core.utils.ResponseResult;
import com.xiaozhi.aoaojiao.model.dto.login.factory.SysLoginDTOFactory;
import com.xiaozhi.aoaojiao.model.vo.SysUserLoginVO;
import com.xiaozhi.aoaojiao.model.vo.SysUserVO;
import com.xiaozhi.aoaojiao.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author xiaozhi
 */
@Tag(name = "系统用户登录接口")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
public class SysLoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private Validator validator;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public ResponseResult<SysUserLoginVO> login(@RequestBody Map<String, String> params) {
        var loginDto = SysLoginDTOFactory.getLoginDto(params);
        var errors = validator.validateObject(loginDto);
        var errorList = errors.getAllErrors()
                .stream().map(ObjectError::getDefaultMessage).toList();
        if (!errorList.isEmpty()) {
            throw new ParameterVerificationException(errorList);
        }
        var sysUserLoginVO = sysUserService.login(loginDto);
        return ResponseResult.success(sysUserLoginVO);
    }

    @Operation(summary = "获取登录用户信息")
    @GetMapping("/getUserInfo")
    public ResponseResult<SysUserVO> getUserInfo() {
        var sysUser = sysUserService.getUserInfo();
        return ResponseResult.success(sysUser);
    }


    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        String token = jwtTokenUtil.getToken(request);
        // 移除登录信息
        redisUtil.del(RedisConstants.getLoginTokenKey(token));
        return ResponseResult.success();
    }

}
