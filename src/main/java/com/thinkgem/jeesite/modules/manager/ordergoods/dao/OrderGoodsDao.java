/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.ordergoods.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manager.ordergoods.entity.OrderGoods;

/**
 * 订单详情DAO接口
 * @author yt
 * @version 2017-09-29
 */
@MyBatisDao
public interface OrderGoodsDao extends CrudDao<OrderGoods> {
	
}