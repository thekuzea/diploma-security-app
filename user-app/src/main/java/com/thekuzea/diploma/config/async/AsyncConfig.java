package com.thekuzea.diploma.config.async;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.thekuzea.diploma.helper.worker.ThreadPoolHelper;

@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig {

    public static final String ASYNC_JOBS_THREAD_POOL_BEAN_BANE = "asyncJobsThreadPoolExecutor";

    @Bean(name = ASYNC_JOBS_THREAD_POOL_BEAN_BANE)
    public Executor asyncJobsThreadPoolExecutor(final AsyncProperties asyncProperties) {
        return ThreadPoolHelper.createThreadPoolTaskExecutor(asyncProperties.getUserJobsThreadPool());
    }
}
