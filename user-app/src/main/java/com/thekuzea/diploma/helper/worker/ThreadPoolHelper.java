package com.thekuzea.diploma.helper.worker;

import java.util.concurrent.ThreadPoolExecutor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.thekuzea.diploma.config.async.AsyncProperties;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ThreadPoolHelper {

    public static ThreadPoolTaskExecutor createThreadPoolTaskExecutor(final AsyncProperties.ThreadPoolSettings settings) {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(settings.getMinPoolSize());
        executor.setMaxPoolSize(settings.getMaxPoolSize());
        executor.setQueueCapacity(settings.getQueueLength());
        executor.setThreadNamePrefix(settings.getThreadNamePrefix());

        executor.setRejectedExecutionHandler(
                (Runnable r, ThreadPoolExecutor e) -> log.error("Task rejected, thread pool is full")
        );

        executor.initialize();

        log.debug(
                "Created thread pool with: min size [{}], max size [{}], queue length [{}], thread name prefix [{}]",
                settings.getMinPoolSize(), settings.getMaxPoolSize(), settings.getQueueLength(), settings.getThreadNamePrefix()
        );

        return executor;
    }
}
