package com.thinkgem.jeesite.mapper;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.vo.OrderLog;
import com.thinkgem.jeesite.vo.OrderLogExample;

import java.util.Date;
import java.util.List;

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


    List<OrderLog> orderLogGroupByAreaId(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}