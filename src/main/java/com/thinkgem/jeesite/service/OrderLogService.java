package com.thinkgem.jeesite.service;

import com.thinkgem.jeesite.mapper.OrderLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by yangtao on 2017/9/27.
 */
@Service
@Transactional
public class OrderLogService {
    @Autowired
    private OrderLogMapper orderLogMapper;

    public void excel(Date startTime, Date endTime, String areaId){
            orderLogMapper.orderLogGroupByAreaId(startTime,endTime);
    }

}
