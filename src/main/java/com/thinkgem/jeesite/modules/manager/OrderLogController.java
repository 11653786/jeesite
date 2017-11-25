package com.thinkgem.jeesite.modules.manager;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.mapper.CabinetHttpLogMapper;
import com.thinkgem.jeesite.mapper.OrderLogMapper;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.CabinetDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
import com.thinkgem.jeesite.service.CabinetHttpLogService;
import com.thinkgem.jeesite.service.OrderLogService;
import com.thinkgem.jeesite.vo.CabinetHttpLog;
import com.thinkgem.jeesite.vo.OrderLog;
import com.thinkgem.jeesite.vo.handler.OrderLogHandler;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by yangtao on 2017/9/25.
 */
@Controller
@RequestMapping(value = "${adminPath}/orderlog/orderlog")
public class OrderLogController extends BaseController {


    @Autowired
    private CabinetService cabinetService;
    @Autowired
    private OrderLogService orderLogService;
    @Autowired
    private OrderLogMapper orderLogMapper;


    @RequiresPermissions("orderlog:orderlog:view")
    @RequestMapping(value = "")
    public String list(Date startTime, Date endTime, String areaId, String cabinetNo, Model model, Integer submitOrderType, @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        Page<OrderLogHandler> page = new Page<OrderLogHandler>();
        if (startTime != null && endTime != null && StringUtils.isNoneBlank(areaId)) {
            page.setPageNo(pageNo);
            if (pageSize < 1) {
                pageSize = 10;
            }
            pageNo = (pageNo - 1) * pageSize;
            page.setPageSize(pageSize);
            List<OrderLogHandler> list = orderLogService.groupByProductNameByAreaId(startTime, endTime, areaId, cabinetNo, submitOrderType, pageNo, pageSize);
            page.setCount(list.size());
            page.setList(list);
        }

        model.addAttribute("page", page);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "manager/orderlog/orderlogList";
    }


    @RequiresPermissions("orderlog:orderlog:export")
    @RequestMapping(value = "/export")
    public String export(Date startTime, Date endTime, String areaId, String cabinetNo, Integer submitOrderType, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {

            String fileName = "统计数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<OrderLogHandler> list = orderLogService.groupByProductNameByAreaId(startTime, endTime, areaId, cabinetNo, submitOrderType, null, null);
            if (!list.isEmpty()) {
                //销售总数量和金额
                OrderLogHandler total = orderLogService.getGroupbyTotal(areaId, cabinetNo, submitOrderType);
                list.add(total);
                new ExportExcel("统计数据", OrderLogHandler.class).setDataList(list).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, e.getMessage());
        }
        return "redirect:" + adminPath + "/orderlog/orderlog?repage";
    }

    @RequiresPermissions("orderlog:orderlog:export")
    @RequestMapping(value = "/getCabinetByAreaId")
    @ResponseBody
    public List<Cabinet> getCabinetByAreaId(String areaId) {
        return cabinetService.getCabinetByAreaId(areaId);
    }

}
