package com.thinkgem.jeesite;

import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.entity.UserRedpacketRelaction;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.service.UserRedpacketRelactionService;
import com.thinkgem.jeesite.modules.manager.users.entity.Users;
import com.thinkgem.jeesite.modules.manager.users.service.UsersService;
import com.thinkgem.jeesite.util.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/10/5.
 */
@Controller
@RequestMapping(value = "/api/wechat")
public class WechatController {

    @Autowired
    private CabinetService cabinetService;
    @Autowired
    private UserRedpacketRelactionService userRedpacketRelactionService;
    @Autowired
    private UsersService usersService;

    Logger logger = Logger.getLogger(this.getClass().getName());


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

    /**
     * 我的红包
     *
     * @return
     */
    @RequestMapping(value = "/redpacket")
    public String redpacket(HttpServletRequest request, Model model,String openid) {
        logger.info("传递过来的openid: "+openid);
        Map<String, String> map = XMLUtil.xmlToMap(request);
        for (String in : map.keySet()) {
            String str = map.get(in);//得到每个key多对用value的值
            logger.info(in + ":" + str);
        }
        Users users = usersService.findByOpenId(map.get("openid"));
        if (users != null) {
            List<UserRedpacketRelaction> userRedpacketRelaction = userRedpacketRelactionService.findByUserId(users.getId());
            if (userRedpacketRelaction != null && !userRedpacketRelaction.isEmpty())
                model.addAttribute("redpackgets", userRedpacketRelaction);
        }
        return "wechat/redpacket";
    }
}
