package com.thinkgem.jeesite.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by yangtao on 2017/10/19.
 */
@Component
@EnableScheduling
public class HttpCabinetStatusTask {

    /**
     * http获取柜子状态是否正常
     */
    @Scheduled(cron = "* * 0/30 * * ? ") // 间隔3分钟执行
    public void work() {

    }
}
