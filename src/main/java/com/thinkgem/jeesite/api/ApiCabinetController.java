package com.thinkgem.jeesite.api;

import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.service.OrderService;
import com.thinkgem.jeesite.modules.manager.drawer.service.DrawerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/10/2.
 */
@Controller
@RequestMapping(value = "/api/cabinet")
public class ApiCabinetController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private DrawerService drawerService;


    /**
     * 取餐
     *
     * @param cabinetNo   柜子编号
     * @param putPassword 取餐密码
     * @return
     */
    @RequestMapping(value = "/outFood")
    @ResponseBody
    public PlatformRes<String> outFood(String cabinetNo, String putPassword) {
        return orderService.outFood(cabinetNo, putPassword);
    }

    @RequestMapping(value = "/putFood")
    @ResponseBody
    public PlatformRes<String> putFood(String cabinetNo,String drawerNo) {
        return drawerService.putFood(cabinetNo,drawerNo);
    }

}



