package com.thinkgem.jeesite.task;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Drawer;
import com.thinkgem.jeesite.modules.manager.drawer.service.DrawerService;
import com.thinkgem.jeesite.modules.manager.ordergoods.entity.OrderGoods;
import com.thinkgem.jeesite.modules.manager.ordergoods.service.OrderGoodsService;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;
import com.thinkgem.jeesite.modules.manager.orders.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 微信公衆號下單，未支付鎖定訂單解鎖
 * Created by yangtao on 2017/10/19.
 */
@Component
@EnableScheduling
public class UnLockDrawerTask {



    private static Logger logger = LoggerFactory.getLogger(UnLockDrawerTask.class);

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderGoodsService orderGoodsService;
    @Autowired
    private DrawerService drawerService;


    /**
     * 微信公众号未支付订单,并且超过5分钟的处理
     */
    @Scheduled(cron = "0 0/3 * * * ?") // 间隔3分钟执行
    public void work() {
        logger.info("UnLockDrawerTask執行時間: "+ DateUtils.formatDateTime(new Date()));
        List<Orders> orders = ordersService.getWechatRepayOrder();
        if (orders != null && !orders.isEmpty()) {
            for (Orders order : orders) {
                String cabinetNo = order.getCabinetNo();
                String drawerNos = "";
                List<OrderGoods> orderGoods = orderGoodsService.getOrderGoodsByOrderNo(order.getOrderNo());
                if (orderGoods != null && !orderGoods.isEmpty()) {
                    for (OrderGoods orderGood : orderGoods) {
                        drawerNos = drawerNos + "," + orderGood.getDrawerNo();
                        Drawer drawer = drawerService.getDrawerByDrawerNo(orderGood.getCabinetNo(), orderGood.getDrawerNo());
                        if (drawer.getFoodStatus().equals("4"))
                            drawer.setFoodStatus("1");
                        drawerService.unlockStatus(drawer.getDrawerNo());


                    }
                }
                //修改订单状态为超时
                order.setOrderStatus(4);
                ordersService.update(order);

                //这里要通知柜子解锁状态
//                cabinetNo,drawerNos
            }
        }
    }


}
