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
    public void submitForOrder(String orderNo, List<PreOrderReq> products, Integer productTotalPrice) {

        Orders orders = new Orders();
        orders.setOrderNo(orderNo);
        orders.setPayMoney(productTotalPrice);
        orders.setActualPayMoney(productTotalPrice);
        orders.setOrderStatus(0);
        orders.setCreateTime(new Date());
        //private String openid;        // 微信标志

        //去重
        for (PreOrderReq req : products) {
            int i = 0;
            for (int j = i + 1; j < products.size(); j++) {
                PreOrderReq compareReq = products.get(j);
                if (req.getProductId().equals(compareReq.getProductId())) {
                    req.setGetProductActualPrice(req.getGetProductActualPrice() + compareReq.getGetProductActualPrice());
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
            orderGoods.setProductNum(1);
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
    }

}