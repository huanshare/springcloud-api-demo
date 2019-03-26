package com.huanshare.test.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by BF400254 on 2018/4/11.
 */
@Configuration
@EnableAsync
public class TaskExecutePool {

    @Bean
    public Executor taskAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        TaskThreadPoolConfig config = new TaskThreadPoolConfig(1, 5, 25, 60);
        executor.setCorePoolSize(config.getCorePoolSize());
        executor.setMaxPoolSize(config.getMaxPoolSize());
        executor.setQueueCapacity(config.getQueueCapacity());
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    private class TaskThreadPoolConfig {
        private int corePoolSize;
        private int maxPoolSize;
        private int keepAliveSeconds;
        private int queueCapacity;

        public TaskThreadPoolConfig(int corePoolSize, int maxPoolSize, int keepAliveSeconds, int queueCapacity) {
            this.corePoolSize = corePoolSize;
            this.maxPoolSize = maxPoolSize;
            this.keepAliveSeconds = keepAliveSeconds;
            this.queueCapacity = queueCapacity;
        }

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public int getKeepAliveSeconds() {
            return keepAliveSeconds;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }
    }
}
