package com.xiaozhi.aoaojiao.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaozhi
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("aoaojiao@163.com");
        contact.setName("xiaozhi");
        contact.setUrl("http://doc.aoaojiao.com");
        return new OpenAPI()
                // 增加swagger授权请求头配置 -> 有权限访问的可以设置
                // .components(new Components().addSecuritySchemes(CommonConstant.X_ACCESS_TOKEN,
                //         new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme(CommonConstant.X_ACCESS_TOKEN)))
                .info(new Info()
                        .title("aoaojiao 后台服务API接口文档")
                        .version("1.0")
                        .contact(contact)
                        .description("aoaojiao 后台服务API接口文档")
                        .termsOfService("http://doc.aoaojiao.com"));
    }
}
