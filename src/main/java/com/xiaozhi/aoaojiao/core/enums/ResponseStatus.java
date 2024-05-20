package com.xiaozhi.aoaojiao.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaozhi
 * 
 * <p>错误码由两部分组成，错误类型和错误编码<p>
 * <p>类型：<p>
 * <p>
 *  A 表示错误源于用户，例如参数错误。
 *  B 表示错误源于当前系统，例如 业务逻辑错误。
 *  C 表示错误源于第三方服务，例如 调佣阿里云OSS超时。
 * <p>
 * <p>错误编码：<p>
 * <p>错误编号是一个在0001到9999之间的四位数，用于进一步细化错误的类别。<p>
 *
 */
@Getter
@AllArgsConstructor
public enum ResponseStatus {

    /**
     * 正确执行后的返回
     */
    SUCCESS("00000", "success"),
    
    USER_UN_AUTHENTICATED("A0401", "用户未认证"),
    USER_UN_UNAUTHORIZED("A0403", "权限不够，无法访问"),
    NOT_FOUND_ERROR("A0404", "访问资源不存在"),


    /**
     * 操作错误 A0600 - A699
     */
    OPERATION_ERROR("A0600", "操作失败，请重试"),
    PARAMETER_VERIFICATION_ERROR("A0601", "参数校验异常"),
    DELETE_OP_ERROR("A0602", "不能删除，存在下级"),
    NAME_REPEAT_ERROR("A0603", "该名称已重复，请重新输入"),


    /**
     * 登录错误 A0460 - A0499
     */
    LOGIN_TYPE_ERROR("A0460", "不支持该登录方式"),
    LOGIN_ERROR("A0461", "登录失败，请联系管理员"),
    LOGIN_UNAME_PWD_ERROR("A0462", "用户名或密码错误"),
    LOGIN_GET_CODE_ERROR("A0463", "获取验证码失败，请联系管理员"),
    LOGIN_CODE_ERROR("A0464", "验证码错误，请重新输入"),
    LOGIN_REPEAT_ERROR("A0465", "重复登录"),
    EMAIL_SEND_ERROR("A0466", "邮箱发送验证码失败，请重试"),
    LOGIN_USER_NOT_EXIST_ERROR("A0467", "系统不存在该用户，请联系管理员"),
    LOGOUT_ERROR("A0467", "退出登录失败"),


    /**
     * 系统错误
     */
    SYSTEM_ERROR("B0001", "系统执行出错，请联系管理员");
    
    
    /**
     * 状态码
     */
    private final String code;
    /**
     * 消息
     */
    private final String message;

}