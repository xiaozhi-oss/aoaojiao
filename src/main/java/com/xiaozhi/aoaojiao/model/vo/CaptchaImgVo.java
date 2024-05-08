package com.xiaozhi.aoaojiao.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xiaozhi
 */
@Data
@AllArgsConstructor
public class CaptchaImgVo {

    private String uuid;

    private String base64Img;
}
