package com.xiaozhi.aoaojiao.core.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiaozhi
 */
@ConfigurationProperties(prefix = "ss")
@Component
@Data
public class SSProperties {

    private List<String> staticPaths;

    private List<String> publicPaths;

}
