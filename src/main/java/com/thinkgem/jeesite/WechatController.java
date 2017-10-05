package com.thinkgem.jeesite;

import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrator on 2017/10/5.
 */
@Controller
@RequestMapping(value = "/api/wechat")
public class WechatController {

    @Autowired
    private CabinetService cabinetService;

    /**
     * 美食介绍
     *
     * @return
     */
    @RequestMapping(value = "/food")
    public String food() {
        return "wechat/food";
    }

    /**
     * 微信公众号品牌页面
     *
     * @return
     */
    @RequestMapping(value = "/brand")
    public String brand() {
        return "wechat/pinpai";
    }

    /**
     * 附近快餐柜
     *
     * @return
     */
    @RequestMapping(value = "/allcabinet")
    public String allcabinet(Model model) {
        List<Cabinet> list = cabinetService.findList(new Cabinet());
        model.addAttribute("cabinets", list);
        return "wechat/allcabinet";
    }
}
