package com.xiaozhi.aoaojiao.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaozhi
 *
 * 项目启动成功打印日志监听器
 */
@Component
public class StartedSuccessListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(StartedSuccessListener.class);

        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            // 获取服务端口号
            String port = event.getApplicationContext().getEnvironment().getProperty("server.port");
        // 服务url
        String serverUrl = String.format("http://%s:%s", "127.0.0.1", port);
        log.info(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE, "your project server started at: ", serverUrl));
        log.info(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE, "your project server's doc started at:", serverUrl + "/doc.html"));
        log.info(AnsiOutput.toString(AnsiColor.BRIGHT_YELLOW, "your project server has started successfully!"));
    }
}
