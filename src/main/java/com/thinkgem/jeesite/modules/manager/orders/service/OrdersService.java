/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.orders.service;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.api.entity.req.PreOrderReq;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.manager.ordergoods.dao.OrderGoodsDao;
import com.thinkgem.jeesite.modules.manager.ordergoods.entity.OrderGoods;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.dao.UserRedpacketRelactionDao;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.entity.UserRedpacketRelaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;
import com.thinkgem.jeesite.modules.manager.orders.dao.OrdersDao;

import javax.swing.*;

/**
 * 订单实体类Service
 *
 * @author yt
 * @version 2017-08-19
 */
@Service
@Transactional(readOnly = true)
public class OrdersService extends CrudService<OrdersDao, Orders> {


    @Autowired
    private OrderGoodsDao orderGoodsDao;
    @Autowired
    private UserRedpacketRelactionDao userRedpacketRelactionDao;


    public Orders get(String id) {
        return super.get(id);
    }

    public List<Orders> findList(Orders orders) {
        return super.findList(orders);
    }

    public Page<Orders> findPage(Page<Orders> page, Orders orders) {
        return super.findPage(page, orders);
    }

    @Transactional(readOnly = false)
    public void save(Orders orders) {
        super.save(orders);
    }

    @Transactional(readOnly = false)
    public void delete(Orders orders) {
        super.delete(orders);
    }

    @Transactional(readOnly = false)
    public Orders submitForOrder(String orderNo, Integer paymentStatus, List<PreOrderReq> products, Integer productTotalPrice, String repackgeId) {

        Orders orders = new Orders();
        orders.setOrderNo(orderNo);
        //取餐密码设置
        String putPassword = (int) ((Math.random() * 9 + 1) * 100000) + "";
        orders.setPutPassword(putPassword);


        //这里判断下红包使用
        UserRedpacketRelaction userRedpacketRelaction = null;
        if (StringUtils.isBlank(repackgeId)) {
            userRedpacketRelaction = userRedpacketRelactionDao.get(repackgeId);
        }

        if (userRedpacketRelaction != null) {

            // 红包类型，1折扣劵，2.优惠卷，3.满减优惠（这个暂时累用）
            if (userRedpacketRelaction.getRedpacketType().equals("1")) {
                Double actualPayMoney = Double.valueOf(productTotalPrice) - Double.valueOf(userRedpacketRelaction.getDiscountRatio()) * Double.valueOf(productTotalPrice);
                orders.setActualPayMoney(actualPayMoney.intValue());
            } else if (userRedpacketRelaction.getRedpacketType().equals("2")) {
                orders.setActualPayMoney(productTotalPrice - userRedpacketRelaction.getRedpacketPrice());
            } else if (userRedpacketRelaction.getRedpacketType().equals("3")) {

            }

            orders.setActualPayMoney(productTotalPrice);
        } else {
            orders.setActualPayMoney(productTotalPrice);
        }
        orders.setPayMoney(productTotalPrice);
        orders.setOrderStatus(0);
        orders.setPaymentStatus(paymentStatus);
        orders.setCreateTime(new Date());
        //微信扫码付,微信公众号支付
        if (paymentStatus == 0) {
            orders.setWechatTradeNo(orderNo);
        } else if (paymentStatus == 1) {
            orders.setWechatTradeNo(orderNo);
//            orders.setOpenid();
        } else if (paymentStatus == 2) {     //支付宝扫码付
            orders.setAlipayTradeNo(orderNo);
        }

        //private String openid;        // 微信标志

        //去重
        for (PreOrderReq req : products) {
            int i = 0;
            for (int j = i + 1; j < products.size(); j++) {
                PreOrderReq compareReq = products.get(j);
                if (req.getProductId().equals(compareReq.getProductId())) {
                    req.setProductNum(req.getProductNum() + compareReq.getProductNum());
                    products.remove(j);
                }
            }
            i++;
        }


        for (PreOrderReq req : products) {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderNo(orderNo);
            orderGoods.setProductId(req.getProductId());
            orderGoods.setProductName(req.getProductName());
            orderGoods.setProductActualPrice(req.getGetProductActualPrice());
            orderGoods.setProductNum(req.getProductNum());
            orderGoods.setAreaId(req.getAreaId());
            orderGoods.setAreaName(req.getAreaName());
            orderGoods.setCabinetNo(req.getCabinetNo());
            orderGoods.setDrawerNo(req.getDrawerNo());
            //设置订单柜子信息
            if (StringUtils.isBlank(orders.getCabinetNo()))
                orders.setCabinetNo(orders.getCabinetNo());

            orderGoodsDao.insert(orderGoods);

        }


        super.save(orders);

        return orders;
    }

}