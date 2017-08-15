/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.orders.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;

/**
 * 订单实体类DAO接口
 * @author yt
 * @version 2017-08-16
 */
@MyBatisDao
public interface OrdersDao extends CrudDao<Orders> {
	
}