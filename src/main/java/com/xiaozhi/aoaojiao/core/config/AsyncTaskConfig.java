package com.xiaozhi.aoaojiao.core.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author xiaozhi
 *
 * 异步任务配置
 */
@EnableAsync
@Configuration
public class AsyncTaskConfig {
    
    @Autowired
    private TaskExecutionProperties tsp;

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(tsp.getPool().getCoreSize());
        executor.setMaxPoolSize(tsp.getPool().getMaxSize());
        executor.setQueueCapacity(tsp.getPool().getQueueCapacity());
        executor.setKeepAliveSeconds(tsp.getPool().getKeepAlive().toSecondsPart());
        executor.setThreadNamePrefix(tsp.getThreadNamePrefix());
        executor.initialize();
        return TtlExecutors.getTtlExecutor(executor);
    }
}
