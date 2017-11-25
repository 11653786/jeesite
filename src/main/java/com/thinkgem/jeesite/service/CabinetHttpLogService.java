package com.thinkgem.jeesite.service;

import com.thinkgem.jeesite.mapper.CabinetHttpLogMapper;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.CabinetDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.vo.CabinetHttpLog;
import com.thinkgem.jeesite.vo.CabinetHttpLogExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by yangtao on 2017/10/19.
 */
@Service
@Transactional
public class CabinetHttpLogService {

    @Autowired
    private CabinetHttpLogMapper cabinetHttpLogMapper;
    @Autowired
    private CabinetDao cabinetDao;


    public CabinetHttpLog findLogByCabinetNo(String cabinetNo) {
        CabinetHttpLogExample example = new CabinetHttpLogExample();
        example.createCriteria().andCabinetnoEqualTo(cabinetNo);
        List<CabinetHttpLog> list = cabinetHttpLogMapper.selectByExample(example);
        if (list == null || list.isEmpty())
            return null;
        else
            return list.get(0);
    }


    public Integer saveOrUpdateCabinetLog(String cabinetNo) {
        Integer isSuccess = 0;
        Cabinet cabinet = cabinetDao.getCabinetByCabinetNo(cabinetNo);
        if (cabinet != null) {
            CabinetHttpLogExample example = new CabinetHttpLogExample();
            example.createCriteria().andCabinetnoEqualTo(cabinetNo);
            List<CabinetHttpLog> list = cabinetHttpLogMapper.selectByExample(example);
            //有就修改,没有就保存
            if (list != null && !list.isEmpty()) {
                CabinetHttpLog cabinetHttpLog = list.get(0);
                cabinetHttpLog.setCreatetime(new Date());
                isSuccess = cabinetHttpLogMapper.updateByPrimaryKeySelective(cabinetHttpLog);
            } else {
                CabinetHttpLog cabinetHttpLog = new CabinetHttpLog();
                cabinetHttpLog.setCabinetno(cabinetNo);
                cabinetHttpLog.setCreatetime(new Date());
                isSuccess = cabinetHttpLogMapper.insert(cabinetHttpLog);
            }

            if (!cabinet.getCabinetStatus().equals("1")) {
                cabinet.setCabinetStatus("1");
                isSuccess = cabinetDao.update(cabinet);
            }
        } else {
            cabinet = new Cabinet();
            cabinet.setCabinetNos(cabinetNo);
            cabinet.preInsert();
            cabinet.setSysPassword("111111");
            cabinet.setFoodPassword("111111");
            cabinet.setWorkStartTime("08:00");
            cabinet.setWorkEndTime("20:00");
            isSuccess= cabinetDao.insert(cabinet);
        }

        return isSuccess;
    }


}
