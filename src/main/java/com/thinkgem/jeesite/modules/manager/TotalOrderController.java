package com.thinkgem.jeesite.modules.manager;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
import com.thinkgem.jeesite.service.OrderLogService;
import com.thinkgem.jeesite.vo.OrderLog;
import com.thinkgem.jeesite.vo.handler.OrderLogHandler;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by yangtao on 2017/9/25.
 */
@Controller
@RequestMapping(value = "${adminPath}/totalorder/totalorder")
public class TotalOrderController extends BaseController {


    @Autowired
    private OrderLogService orderLogService;
    @Autowired
    private CabinetService cabinetService;


    @RequiresPermissions("totalorder:totalorder:view")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list() {
        return "manager/totalorder/totalordersList";
    }


    @RequiresPermissions("totalorder:totalorder:export")
    @RequestMapping(value = "/export")
    public String export(Date startTime, Date endTime, String areaId, String cabinetNo, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "统计数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<OrderLogHandler> list = orderLogService.groupByProductNameByAreaId(startTime, endTime, areaId, cabinetNo);
            if (!list.isEmpty()) {
                //销售总数量和金额
                OrderLogHandler total = orderLogService.getGroupbyTotal(areaId, cabinetNo);
                list.add(total);
                new ExportExcel("统计数据", OrderLogHandler.class).setDataList(list).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, e.getMessage());
        }
        return "redirect:" + adminPath + "/totalorder/totalorder?repage";
    }

    @RequiresPermissions("totalorder:totalorder:export")
    @RequestMapping(value = "/getCabinetByAreaId")
    @ResponseBody
    public List<Cabinet> getCabinetByAreaId(String areaId) {
        return cabinetService.getCabinetByAreaId(areaId);
    }

}
