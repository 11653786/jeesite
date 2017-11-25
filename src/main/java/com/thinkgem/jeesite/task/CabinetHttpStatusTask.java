package com.thinkgem.jeesite.task;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.CabinetDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
import com.thinkgem.jeesite.service.CabinetHttpLogService;
import com.thinkgem.jeesite.vo.CabinetHttpLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(CabinetHttpStatusTask.class);


    @Autowired
    private CabinetHttpLogService cabinetHttpLogService;
    @Autowired
    private CabinetService cabinetService;
    @Autowired
    private CabinetDao cabinetDao;

    /**
     * http获取柜子状态是否正常
     */
    @Scheduled(cron = "0 0/1 * * * ?") // 间隔30分钟执行
    public void work() {
        logger.info("CabinetHttpStatusTask執行時間: "+ DateUtils.formatDateTime(new Date()));
        List<Cabinet> cabinetList = cabinetService.findList(new Cabinet());
        for (Cabinet cabinet : cabinetList) {
            CabinetHttpLog cabinetHttpLog = cabinetHttpLogService.findLogByCabinetNo(cabinet.getCabinetNos());
            //超過5個小時就算通信異常
            if (cabinetHttpLog != null) {
                if (DateUtils.getBeteenHour(new Date(), cabinetHttpLog.getCreatetime()) < 1) {
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
