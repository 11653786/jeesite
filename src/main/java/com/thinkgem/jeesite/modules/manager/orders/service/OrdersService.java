/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.orders.service;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.api.entity.req.PreOrderReq;
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

        for (PreOrderReq req : products) {

        }



        super.save(orders);
    }

}