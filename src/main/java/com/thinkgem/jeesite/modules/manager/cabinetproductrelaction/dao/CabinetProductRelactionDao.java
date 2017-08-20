/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.entity.CabinetProductRelaction;
import org.apache.ibatis.annotations.Param;

/**
 * 柜子商品关系配置DAO接口
 * @author yt
 * @version 2017-08-19
 */
@MyBatisDao
public interface CabinetProductRelactionDao extends CrudDao<CabinetProductRelaction> {

    CabinetProductRelaction findBycabinetIdAndProductId(@Param("cabinetId")String cabinetId,@Param("productId") String productId);
	
}