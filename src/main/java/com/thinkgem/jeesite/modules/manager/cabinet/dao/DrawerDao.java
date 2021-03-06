/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinet.dao;

import com.thinkgem.jeesite.api.entity.handler.CabinerDrawerHandler;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Drawer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 抽屉DAO接口
 *
 * @author yt
 * @version 2017-08-19
 */
@MyBatisDao
public interface DrawerDao extends CrudDao<Drawer> {

    /**
     * 根据柜子和抽屉查询当前柜子是否存在
     *
     * @param cabinetNo
     * @param drawerNo
     * @return
     */
    CabinerDrawerHandler findCabinetAndDrawerInfo(@Param("cabinetNo") String cabinetNo, @Param("drawerNo") String drawerNo);

    Drawer findCabinetAndDrawerNo(@Param("cabinetNo") String cabinetNo, @Param("drawerNo") String drawerNo);

    /**
     * 取餐
     *
     * @param cabinetNo
     * @param drawerNo
     * @return
     */
    Integer outFood(@Param("cabinetNo") String cabinetNo, @Param("drawerNo") String drawerNo);

    /**
     * 放餐
     *
     * @param cabinetNo
     * @param drawerNo
     * @return
     */
    Integer putFood(@Param("productId") String productId, @Param("productName") String productName, @Param("cabinetNo") String cabinetNo, @Param("drawerNo") String drawerNo);

    /**
     * 获取可以购买当前商品的柜子
     *
     * @param productId
     * @param cabinetId
     * @return
     */
    List<Drawer> getDrawerBuy(@Param("productId") String productId, @Param("cabinetId") String cabinetId);

    /**
     * 微信公众号支付用来锁定抽屉,定时器默认锁定5分钟
     * @param drawerNo
     * @param foodStatus
     * @return
     */
    Integer lockOrUnlockStatus(@Param("id") String id, @Param("foodStatus") Integer foodStatus);

    List<Drawer> findDrawerOutTimeList();

    Integer update1(Drawer drawer);


}