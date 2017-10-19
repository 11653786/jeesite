package com.thinkgem.jeesite.task;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.CabinetDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
import com.thinkgem.jeesite.service.CabinetHttpLogService;
import com.thinkgem.jeesite.vo.CabinetHttpLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by yangtao on 2017/10/19.
 */
@Component
@EnableScheduling
public class CabinetHttpStatusTask {


    @Autowired
    private CabinetHttpLogService cabinetHttpLogService;
    @Autowired
    private CabinetService cabinetService;
    @Autowired
    private CabinetDao cabinetDao;

    /**
     * http获取柜子状态是否正常
     */
    @Scheduled(cron = "* * 0/1 * * ? ") // 间隔1小時执行
    public void work() {
        List<Cabinet> cabinetList = cabinetService.findList(new Cabinet());
        for (Cabinet cabinet : cabinetList) {
            CabinetHttpLog cabinetHttpLog = cabinetHttpLogService.findLogByCabinetNo(cabinet.getCabinetNos());
            //超過兩個小時就算通信異常
            if (cabinetHttpLog != null) {
                if (DateUtils.getBeteenHour(new Date(), cabinetHttpLog.getCreatetime()) > 5) {
                    cabinet.setCabinetStatus("2");
                    cabinetDao.update(cabinet);
                }
            } else {
                //沒有日志就保存成未通信
                cabinet.setCabinetStatus("0");
                cabinetDao.update(cabinet);
            }


        }
    }
}
