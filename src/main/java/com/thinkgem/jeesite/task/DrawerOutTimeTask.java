package com.thinkgem.jeesite.task;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.DrawerDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Drawer;
import com.thinkgem.jeesite.modules.manager.orders.dao.OrdersDao;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by yangtao on 2017/10/19.
 */
@Component
@EnableScheduling
public class DrawerOutTimeTask {

    private static Logger logger = LoggerFactory.getLogger(DrawerOutTimeTask.class);


    @Autowired
    private DrawerDao drawerDao;
    @Autowired
    private OrdersDao ordersDao;

    /**
     * http获取柜子状态是否正常
     */
//    @Scheduled(cron = "0 0/30 * * * ?") // 间隔30分钟执行
    public void work() {
        logger.info("DrawerOutTimeTask执行时间: " + DateUtils.formatDateTime(new Date()));
        List<Drawer> drawers = drawerDao.findDrawerOutTimeList();
        for (Drawer drawer : drawers) {
            //查看当前抽屉的商品有没有被人预定,如果被预定了就走订单超时定时器
           List<Orders> orders= ordersDao.getWechatPayOrders(drawer.getCabinetNo(),drawer.getDrawerNo());
            if(orders==null || orders.isEmpty()){
                drawerDao.lockOrUnlockStatus(drawer.getId(), 2);
            }
        }
    }
}
