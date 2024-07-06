package com.thekuzea.diploma.config.async;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "local-wall.async")
public class AsyncProperties {

    private ThreadPoolSettings userJobsThreadPool;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ThreadPoolSettings {

        private static final int DEFAULT_MIN_POOL_SIZE = 1;

        private static final int DEFAULT_MAX_POOL_SIZE = 5;

        private static final int DEFAULT_QUEUE_LENGTH = 10;

        private static final String DEFAULT_THREAD_NAME_PREFIX = "defaultWorker";

        private int minPoolSize = DEFAULT_MIN_POOL_SIZE;

        private int maxPoolSize = DEFAULT_MAX_POOL_SIZE;

        private int queueLength = DEFAULT_QUEUE_LENGTH;

        private String threadNamePrefix = DEFAULT_THREAD_NAME_PREFIX;
    }
}
