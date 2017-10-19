/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.orders.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单实体类DAO接口
 *
 * @author yt
 * @version 2017-08-19
 */
@MyBatisDao
public interface OrdersDao extends CrudDao<Orders> {

    /**
     * 根据订单号查询订单
     *
     * @param orderNo
     * @return
     */
    Orders getOrdersByOrderNo(@Param("orderNo") String orderNo);

    /**
     * @param cabinetNo putPassword
     * @return
     */
    Orders getOrdersByPassAndCabinetNo(@Param("cabinetNo") String cabinetNo, @Param("putPassword") String putPassword);

    /**
     * 根据openId获取已支付id
     *
     * @param openId
     * @return
     */
    List<Orders> getPayOrders(@Param("openId") String openId);

    List<Orders> getOrderDetail(@Param("openId") String openId);

    /**
     * 微信公众号未支付,并且超过5分钟的订单
     * @return
     */
    List<Orders> getWechatRepayOrder();
}