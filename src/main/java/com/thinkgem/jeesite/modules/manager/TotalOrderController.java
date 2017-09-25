package com.thinkgem.jeesite.modules.manager;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.manager.orders.service.OrdersService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by yangtao on 2017/9/25.
 */
@Controller
@RequestMapping(value = "${adminPath}/totalorder/totalorder")
public class TotalOrderController extends BaseController {


    @Autowired
    private OrdersService orderService;

    @RequiresPermissions("totalorder:totalorder:view")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list() {
        return "manager/totalorder/totalordersList";
    }


    @RequiresPermissions("totalorder:totalorder:export")
    @RequestMapping(value = "/export")
    public String export(Date startTime, Date endTime,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "统计数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
//            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
//            new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/totalorder/totalorder?repage";
    }

}
