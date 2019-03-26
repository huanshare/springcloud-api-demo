package com.huanshare.test.business.aop;

import com.huanshare.test.business.service.EnumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledFuture;

/**
 * 初始化内存值集操作
 * <p>
 *
 *
 * created on 2018/9/10 14:34
 */
@Component
@Slf4j
@Order(value = 1)
public class StartRunMethod implements ApplicationRunner {

    @Resource
    private EnumService enumService;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }


    @Override
    public void run(ApplicationArguments applicationArguments) {
        try {
            enumService.initLookupCodes();
        } catch (Exception e) {
            log.error("值集启动异常，5分钟后重试");
            future = threadPoolTaskScheduler.schedule(new InitLookupCodesRunnable(), new CronTrigger("0 0/5 * * * *"));
        }
    }

    private class InitLookupCodesRunnable implements Runnable {
        @Override
        public void run() {
            try {
                enumService.initLookupCodes();
                if (future != null) {
                    future.cancel(true);
                    log.info("关闭值集重试机制");
                }
            } catch (Exception e) {
                log.error("值集启动异常，5分钟后重试");
            }
        }
    }
}