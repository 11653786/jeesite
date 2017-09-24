/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinet.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;

import java.util.List;

/**
 * 快餐柜实体类DAO接口
 * @author yt
 * @version 2017-08-19
 */
@MyBatisDao
public interface CabinetDao extends CrudDao<Cabinet> {

    /**
     * 统计每个柜子的所有商品
     * @return
     */
    List<Cabinet> groupByProductNameTotal();
	
}