/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.orders.service;

import com.thinkgem.jeesite.api.entity.req.PreOrderReq;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.DrawerDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Drawer;
import com.thinkgem.jeesite.modules.manager.ordergoods.dao.OrderGoodsDao;
import com.thinkgem.jeesite.modules.manager.ordergoods.entity.OrderGoods;
import com.thinkgem.jeesite.modules.manager.orders.dao.OrdersDao;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.entity.UserRedpacketRelaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    private OrdersDao ordersDao;
    @Autowired
    private DrawerDao drawerDao;

    @Transactional(readOnly = false)
    public void update(Orders orders) {
        ordersDao.update(orders);
    }


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
    public Orders submitForOrder(String orderNo, Integer paymentStatus, List<PreOrderReq> products, Integer productTotalPrice, UserRedpacketRelaction userRedpacketRelaction) {

        Orders orders = new Orders();
        orders.setOrderNo(orderNo);
        //取餐密码设置
        String putPassword = (int) ((Math.random() * 9 + 1) * 100000) + "";
        orders.setPutPassword(putPassword);
        orders.setPayMoney(productTotalPrice);

        if (userRedpacketRelaction != null) {

            // 红包类型，1折扣劵，2.优惠卷，3.满减优惠（这个暂时累用）
            if (userRedpacketRelaction.getRedpacketType().equals("1")) {
                Double actualPayMoney = Double.valueOf(productTotalPrice) - Double.valueOf(userRedpacketRelaction.getDiscountRatio()) * Double.valueOf(productTotalPrice);
                orders.setActualPayMoney(actualPayMoney.intValue());
                orders.setRedpacketId(userRedpacketRelaction.getRedpacketId());
                orders.setRedpacketName(userRedpacketRelaction.getRedpacketName());
            } else if (userRedpacketRelaction.getRedpacketType().equals("2")) {
                orders.setActualPayMoney(productTotalPrice - userRedpacketRelaction.getRedpacketPrice());
                orders.setRedpacketId(userRedpacketRelaction.getRedpacketId());
                orders.setRedpacketName(userRedpacketRelaction.getRedpacketName());
            } else if (userRedpacketRelaction.getRedpacketType().equals("3")) {

            }

        } else {
            orders.setActualPayMoney(productTotalPrice);
        }
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

        String cabinetNo = "";
        for (PreOrderReq req : products) {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderNo(orderNo);
            orderGoods.setProductId(req.getProductId());
            orderGoods.setProductName(req.getProductName());
            orderGoods.setProductPrice(req.getGetProductActualPrice());
            orderGoods.setProductNum(req.getProductNum());
            orderGoods.setAreaId(req.getAreaId());
            orderGoods.setAreaName(req.getAreaName());
            orderGoods.setCabinetNo(req.getCabinetNo());
            orderGoods.setDrawerNo(req.getDrawerNo());
            orderGoods.setCreateTime(new Date());
            orderNo = orderNo + req.getDrawerNo();
            //设置订单柜子信息
            orders.setCabinetNo(orderGoods.getCabinetNo());
            //生成id
            orderGoods.preInsert();
            orderGoodsDao.insert(orderGoods);
            cabinetNo = req.getCabinetNo();
            //
            //修改柜子编号
//            drawerDao.putFood(req.getCabinetNo(),req.getDrawerNo());
        }
        orderNo = orderNo + cabinetNo;
        orders.setOrderNo(orderNo);

        super.save(orders);

        return orders;
    }

    /**
     * 获取新建订单
     *
     * @return
     */
    public List<Orders> getWechatRepayOrder() {
        return ordersDao.getWechatRepayOrder();
    }


    public List<Orders> getOrderDetail(String openId) {
        return ordersDao.getOrderDetail(openId);
    }

    @Transactional(readOnly = false)
    public void drawerOutTimeProcess() {
        //查詢已經支付并且超過兩小時的訂單
        List<Orders> ordersList = ordersDao.getPayOutTimeOrders();
        for (Orders orders : ordersList) {
            List<OrderGoods> orderGoods = orderGoodsDao.findListByOrderNo(orders.getOrderNo());
            for (OrderGoods orderGood : orderGoods) {
                Drawer drawer = drawerDao.findCabinetAndDrawerNo(orderGood.getCabinetNo(), orderGood.getDrawerNo());
                if (drawer != null) {
                    drawer.setFoodStatus("2");
                    drawerDao.update(drawer);
                }
            }
            orders.setOrderStatus(5);
            ordersDao.update(orders);
        }
    }

}