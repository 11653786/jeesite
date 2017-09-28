package com.thinkgem.jeesite.service;

import com.thinkgem.jeesite.mapper.OrderLogMapper;
import com.thinkgem.jeesite.vo.handler.OrderLogHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by yangtao on 2017/9/27.
 */
@Service
@Transactional
public class OrderLogService {
    @Autowired
    private OrderLogMapper orderLogMapper;


    /**
     * excel 统计
     * @param startTime
     * @param endTime
     * @param areaId
     * @param cabinetNo
     * @return
     */
    public List<OrderLogHandler> groupByProductNameByAreaId(Date startTime, Date endTime, String areaId, String cabinetNo) {
        return orderLogMapper.groupByProductNameByAreaId(areaId, cabinetNo, startTime, endTime);
    }

    /**
     *
     * @return
     */
    public OrderLogHandler getGroupbyTotal(String areaId,String cabinetNo){
        return orderLogMapper.getGroupbyTotal(areaId,cabinetNo);
    }

}
