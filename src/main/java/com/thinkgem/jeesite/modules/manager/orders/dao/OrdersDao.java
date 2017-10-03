/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.orders.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;
import org.apache.ibatis.annotations.Param;

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
     *
     * @param cabinetNo
     * putPassword
     * @return
     */
    Orders getOrdersByPassAndCabinetNo(@Param("cabinetNo") String cabinetNo,@Param("putPassword") String putPassword);
}