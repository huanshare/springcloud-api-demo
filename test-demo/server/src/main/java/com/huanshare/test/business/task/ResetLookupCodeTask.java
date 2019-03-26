package com.huanshare.test.business.task;

import com.huanshare.test.business.service.EnumService;
import com.huanshare.test.client.common.util.DateFormat;
import com.huanshare.test.client.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 值集code定时任务
 *
 * created on 2017/12/22 11:45
 *
 *
 * @version 1.0.0
 */
@Component
@Slf4j
public class ResetLookupCodeTask {
    @Autowired
    private EnumService enumService;

    /**
     * 朝九晚九工作时间内每半小时
     */
    @Scheduled(cron = "0 0/30 9-21 * * ?")
    public void executeLookupTask() {
        log.info("ResetLookupCodeTask.executeLookupTask 定时任务: startTime:{}", DateUtil.formatJustDate(DateFormat.SETTLE_PATTERN));
        enumService.initLookupCodes();
        log.info("ResetLookupCodeTask.executeLookupTask 定时任务: endTime:{}", DateUtil.formatJustDate(DateFormat.SETTLE_PATTERN));
    }
}