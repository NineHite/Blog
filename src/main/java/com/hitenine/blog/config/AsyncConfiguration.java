package com.hitenine.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 开启异步
 *
 * @author Hitenine
 * @date 2021/1/30 22:34
 * @version 1.0
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("blog_task_worker-");
        executor.setQueueCapacity(30);
        executor.initialize();
        return executor;
    }
}
