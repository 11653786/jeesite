/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.product.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manager.product.entity.Product;

/**
 * 商品实体类DAO接口
 * @author yt
 * @version 2017-08-19
 */
@MyBatisDao
public interface ProductDao extends CrudDao<Product> {
	
}