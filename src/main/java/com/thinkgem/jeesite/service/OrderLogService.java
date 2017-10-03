package com.thinkgem.jeesite.service;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.mapper.OrderLogMapper;
import com.thinkgem.jeesite.modules.manager.ordergoods.entity.OrderGoods;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;
import com.thinkgem.jeesite.vo.OrderLog;
import com.thinkgem.jeesite.vo.handler.OrderLogHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by yangtao on 2017/9/27.
 */
@Service
@Transactional
public class OrderLogService {
    @Autowired
    private OrderLogMapper orderLogMapper;


    /**
     * excel 统计
     * @param startTime
     * @param endTime
     * @param areaId
     * @param cabinetNo
     * @return
     */
    public List<OrderLogHandler> groupByProductNameByAreaId(Date startTime, Date endTime, String areaId, String cabinetNo,Integer submitOrderType) {
        return orderLogMapper.groupByProductNameByAreaId(areaId, cabinetNo, startTime, endTime,submitOrderType);
    }

    /**
     *
     * @return
     */
    public OrderLogHandler getGroupbyTotal(String areaId,String cabinetNo,Integer submitOrderType){
        return orderLogMapper.getGroupbyTotal(areaId,cabinetNo,submitOrderType);
    }

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
        orderLog.setOrderNo(orders.getOrderNo());
        orderLog.setAreaId(orderGood.getAreaId());
        orderLog.setAreaName(orderGood.getAreaName());
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
        orderLogMapper.insert(orderLog);
        return orderLog;
    }

}
