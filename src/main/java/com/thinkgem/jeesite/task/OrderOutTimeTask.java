package com.thinkgem.jeesite.task;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.manager.orders.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 已支付訂單超時，設置抽屜狀態為超時
 * Created by yangtao on 2017/10/19.
 */
@Component
@EnableScheduling
public class OrderOutTimeTask {


    private static Logger logger = LoggerFactory.getLogger(OrderOutTimeTask.class);


    @Autowired
    private OrdersService ordersService;

    /**
     * 過期訂單處理
     */
    @Scheduled(cron = "0 0 0/1 * * ?") // 间隔1小時执行
    public void work() {
        logger.info("OrderOutTimeTask執行時間: "+ DateUtils.formatDateTime(new Date()));
        ordersService.drawerOutTimeProcess();
    }
}
