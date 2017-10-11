package com.thinkgem.jeesite.api;

import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.service.OrderService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
import com.thinkgem.jeesite.modules.manager.ordergoods.service.OrderGoodsService;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.entity.UserRedpacketRelaction;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.service.UserRedpacketRelactionService;
import com.thinkgem.jeesite.modules.manager.users.entity.Users;
import com.thinkgem.jeesite.modules.manager.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderGoodsService orderGoodsService;

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
    public String redpacket(Model model, String openid) {
        logger.info("传递过来的openid: " + openid);
        Users users = usersService.findByOpenId(openid);
        if (users != null) {
            List<UserRedpacketRelaction> userRedpacketRelaction = userRedpacketRelactionService.findByUserId(users.getId());
            if (userRedpacketRelaction != null && !userRedpacketRelaction.isEmpty())
                model.addAttribute("redpackgets", userRedpacketRelaction);
        }
        return "wechat/redpacket";
    }

    @RequestMapping(value = "/myorder")
    public String myorder(Model model, String openid) {
        logger.info("传递过来的openid: " + openid);
        List<Orders> orders = orderService.getOrderDetail(openid);
        model.addAttribute("orders", orders);
        return "wechat/myorder";
    }

    /**
     * 退款按钮
     *
     * @param model
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "/refundOrder")
    public String refundOrder(Model model, Integer type, String orderNo) {
        Orders orders = orderService.getOrderByOrderNo(orderNo);
        model.addAttribute("orders", orders);
        model.addAttribute("type", type);
        return "wechat/refundpingjia";
    }


    /**
     * 申请退款和评价
     *
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "/refundPingjia")
    @ResponseBody
    public String refundOrderSubmit(String orderNo, Integer type, String remark) {
        orderService.updateRemark(orderNo, type, remark);
        return "success";
    }


    @RequestMapping(value = "/shopping", method = RequestMethod.GET)
    public String shopping(String openid, Model model) {
        List<UserRedpacketRelaction> redpacketRelactions = userRedpacketRelactionService.findEnableRedpacket(openid);
        if (redpacketRelactions != null && !redpacketRelactions.isEmpty()) {
            model.addAttribute("redpacketRelactions", redpacketRelactions);
            model.addAttribute("openid", openid);
        }
        return "wechat/shopping";
    }


    @RequestMapping(value = "/shopping", method = RequestMethod.POST)
    public String shopping(String[] ids, String[] nums, String cabinetId, String red, String openid, Model model) {
        PlatformRes<String> result = orderService.validPreOrder(ids, nums, cabinetId, red);
        if (result.getCode().equals("0"))
            //这里应该跳转到微信下单的controller里去
            return "";
        else {
            model.addAttribute("message", result.getMessage());
            return "redirect:/api/wechat/shopping?openid=" + openid;
        }
    }


    @RequestMapping(value = "/validPreOrder", method = RequestMethod.POST)
    public PlatformRes<String> validPreOrder(String[] ids, String[] nums, String cabinetId, String red, String openid, Model model) {
        return orderService.validPreOrder(ids, nums, cabinetId, red);
    }

}
