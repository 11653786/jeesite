package com.thinkgem.jeesite.mapper;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.vo.OrderLog;
import com.thinkgem.jeesite.vo.OrderLogExample;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.vo.handler.OrderLogHandler;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface OrderLogMapper {
    int countByExample(OrderLogExample example);

    int deleteByExample(OrderLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderLog record);

    int insertSelective(OrderLog record);

    List<OrderLog> selectByExample(OrderLogExample example);

    OrderLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderLog record, @Param("example") OrderLogExample example);

    int updateByExample(@Param("record") OrderLog record, @Param("example") OrderLogExample example);

    int updateByPrimaryKeySelective(OrderLog record);

    int updateByPrimaryKey(OrderLog record);

    /**
     * 统计区域或者柜子的销售统计
     *
     * @param areaId    地区
     * @param cabinetNo 柜子
     * @param startTime 开始时间
     * @param endTime   截止时间
     * @param submitOrderType 下单类型0,非会员下单,1会员下单
     * @return
     */
    List<OrderLogHandler> groupByProductNameByAreaId(@Param("areaId") String areaId, @Param("cabinetNo") String cabinetNo, @Param("startTime") Date startTime, @Param("endTime") Date endTime,@Param("submitOrderType")Integer submitOrderType);

    /**
     * 统计总数量和金额
     * @param areaId
     * @param cabinetNo
     * @param submitOrderType 下单类型0,非会员下单,1会员下单
     * @return
     */
    OrderLogHandler getGroupbyTotal(@Param("areaId") String areaId, @Param("cabinetNo") String cabinetNo,@Param("submitOrderType")Integer submitOrderType);
}