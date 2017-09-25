/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinet.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 快餐柜实体类DAO接口
 *
 * @author yt
 * @version 2017-08-19
 */
@MyBatisDao
public interface CabinetDao extends CrudDao<Cabinet> {

    /**
     * 统计每个柜子的所有商品
     *
     * @return
     */
    List<Cabinet> groupByProductNameTotal();



    /**
     * 根据放餐状态查询柜子编号和对应的柜子编号对应的状态
     *
     * @param foodStatus 放餐状态:0,未放餐,1已放餐,2已过期',
     */
    List<Cabinet> groupbyCabinetNosByFoodStatus(@Param("foodStatus") Integer foodStatus);

    /**
     * 修改系统密码和取餐密码
     *
     *
     * @param sysPassword
     * @param foodPassword
     * @return
     */
    Integer updatePassword(@Param("type")Integer type,@Param("cabinetId") String cabinetId,@Param("sysPassword") String sysPassword, @Param("foodPassword") String foodPassword);


}