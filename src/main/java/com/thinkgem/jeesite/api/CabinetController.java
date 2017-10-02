package com.thinkgem.jeesite.api;

import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/10/2.
 */
@Controller
@RequestMapping(value = "/api/cabinet")
public class CabinetController {

    @Autowired
    private OrderService orderService;


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

}



