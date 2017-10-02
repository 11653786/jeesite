package com.thinkgem.jeesite.api.service;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.mapper.OrderLogMapper;
import com.thinkgem.jeesite.modules.manager.ordergoods.entity.OrderGoods;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;
import com.thinkgem.jeesite.vo.OrderLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/2.
 */
@Service
public class OrderLogService {

    @Autowired
    private OrderLogMapper orderLogDao;

    /**
     * 付款后用来保存订单日志给保存的excel
     * @param orders
     * @param orderGoodsSize
     * @param orderGood
     * @return
     */
    public OrderLog saveOrderLog(Orders orders, Integer orderGoodsSize, OrderGoods orderGood) {
        //循环取餐

        //保存order_log,一条商品记录一条
        OrderLog orderLog = new OrderLog();
        orderLog.setAreaId(orderGood.getAreaId());
        orderLog.setCabinetNo(orderGood.getCabinetNo());
        orderLog.setCabinetName(orders.getCabinetNo());
        orderLog.setProductId(orderGood.getProductId());
        orderLog.setProductName(orderGood.getProductName());
        orderLog.setProductNum(1);
        orderLog.setCreateTime(new Date());
        if (StringUtils.isBlank(orders.getRedpacketId()))
            orderLog.setProductActualPrice(orderGood.getProductPrice());
        else {
            //计算优惠金额
            Integer redpackgetMoney = orders.getPayMoney() - orders.getActualPayMoney();
            redpackgetMoney = redpackgetMoney / orderGoodsSize;
            orderLog.setProductActualPrice(orderGood.getProductPrice() - redpackgetMoney);
        }

        orderLog.setProductPrice(orderGood.getProductPrice());
        orderLog.setPaymentType(orders.getPaymentStatus());
        orderLog.setPaymentTime(orders.getPaymentTime());
        orderLogDao.insert(orderLog);
        return orderLog;
    }
}
