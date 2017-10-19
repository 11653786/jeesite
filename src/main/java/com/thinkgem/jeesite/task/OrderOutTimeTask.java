package com.thinkgem.jeesite.task;

import com.thinkgem.jeesite.modules.manager.orders.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 已支付訂單超時，設置抽屜狀態為超時
 * Created by yangtao on 2017/10/19.
 */
public class OrderOutTimeTask {


    @Autowired
    private OrdersService ordersService;

    /**
     * 過期訂單處理
     */
    public void work() {
        ordersService.drawerOutTimeProcess();
    }
}
