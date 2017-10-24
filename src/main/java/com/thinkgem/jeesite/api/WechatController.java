package com.thinkgem.jeesite.api;

import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.service.OrderService;
import com.thinkgem.jeesite.api.service.WechatApiService;
import com.thinkgem.jeesite.api.service.WechatPayService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.service.CabinetService;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.dao.CabinetProductRelactionDao;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.entity.CabinetProductRelaction;
import com.thinkgem.jeesite.modules.manager.orders.entity.Orders;
import com.thinkgem.jeesite.modules.manager.product.entity.Product;
import com.thinkgem.jeesite.modules.manager.product.service.ProductService;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.entity.UserRedpacketRelaction;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.service.UserRedpacketRelactionService;
import com.thinkgem.jeesite.modules.manager.users.entity.Users;
import com.thinkgem.jeesite.modules.manager.users.service.UsersService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private WechatPayService wechatPayService;
    @Autowired
    private CabinetProductRelactionDao cabinetProductRelactionDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private WechatApiService wechatApiService;


    Logger logger = Logger.getLogger(this.getClass().getName());


    /**
     * 美食介绍
     *
     * @return
     */
    @RequestMapping(value = "/food")
    public String food(Model model) {
        Product product = new Product();
        product.setProductStatus(1 + "");
        model.addAttribute("products", productService.findList(product));
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
    public String redpacket(Model model, String code) {
        logger.info("我的红包获取的code: " + code);
        String openid = wechatApiService.getOpenIdByCode(code);
        logger.info("我的红包获取的openId: " + code);
        Users users = usersService.findByOpenId(openid);
        if (users != null) {
            List<UserRedpacketRelaction> userRedpacketRelaction = userRedpacketRelactionService.findEnableRedpacket(users.getOpenid());
            if (userRedpacketRelaction != null && !userRedpacketRelaction.isEmpty())
                model.addAttribute("redpackgets", userRedpacketRelaction);
        }
        return "wechat/redpacket";
    }

    /**
     * 我的订单
     *
     * @param model
     * @param code
     * @return
     */
    @RequestMapping(value = "/myorder")
    public String myorder(Model model, String code) {
        List<Orders> orders = new ArrayList<Orders>();
        logger.info("传递过来的code: " + code);
        if (StringUtils.isNotBlank(code)) {
            String openid = wechatApiService.getOpenIdByCode(code);
            logger.info("获取的openid: " + openid);
            orders = orderService.getOrderDetail(openid);
        }

        model.addAttribute("orders", orders);

        return "wechat/myorder";
    }

    /**
     * 退款和评价页面
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
     * 申请退款和评价提交
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


    /**
     * 微信公众号下单页面
     *
     * @param openid
     * @param model
     * @return
     */
    @RequestMapping(value = "/shopping", method = RequestMethod.GET)
    public String shopping(String code, Model model) {
        logger.info("下单取的code: " + code);
        String openid = wechatApiService.getOpenIdByCode(code);
        logger.info("下单获取的openId: " + code);
        List<UserRedpacketRelaction> redpacketRelactions = userRedpacketRelactionService.findEnableRedpacket(openid);
        if (redpacketRelactions != null && !redpacketRelactions.isEmpty()) {
            model.addAttribute("redpacketRelactions", redpacketRelactions);
            model.addAttribute("openid", openid);
        }
        return "wechat/shopping";
    }


    /**
     * 微信公众号下单提交
     *
     * @param ids
     * @param nums
     * @param cabinetId
     * @param red
     * @param openid
     * @param model
     * @return
     */
    @RequestMapping(value = "/shopping", method = RequestMethod.POST)
    public String shopping(String[] ids, String[] nums, String cabinetId, String red, String openid, Model model) {
        PlatformRes<String> result = orderService.validPreOrder(ids, nums, cabinetId, red);
        if (result.getCode().equals("0")) {
            //生成订单,这里应该跳转到微信下单的controller里去
            PlatformRes<Orders> orders = orderService.wechatPublicPreorder(ids, nums, cabinetId, red, openid);
            if (orders.getCode() == "0")
                return "redirect:/api/wechat/submit?orderNo=" + orders.getData().getOrderNo();
            else {
                model.addAttribute("message", result.getMessage());
                return "redirect:/api/wechat/shopping?openid=" + openid;
            }
        } else {
            model.addAttribute("message", result.getMessage());
            return "redirect:/api/wechat/shopping?openid=" + openid;
        }
    }


    /**
     * 微信公众号验证下单
     *
     * @param ids
     * @param nums
     * @param cabinetId
     * @param red
     * @param openid
     * @param model
     * @return
     */
    @RequestMapping(value = "/validPreOrder", method = RequestMethod.POST)
    @ResponseBody
    public PlatformRes<String> validPreOrder(String[] ids, String[] nums, String cabinetId, String red, String openid, Model model) {
        return orderService.validPreOrder(ids, nums, cabinetId, red);
    }

    /**
     * 微信公众号生成以后跳转支付页面!
     *
     * @param orderNo
     * @param model
     * @return
     */
    @RequestMapping(value = "/submit")
    public String submit(String orderNo, Model model) {
        Orders orders = orderService.getOrderByOrderNo(orderNo);
        model.addAttribute("orders", orders);
        return "wechat/submit";
    }


    /**
     * 获取西安市地区信息
     *
     * @return
     */
    @RequestMapping(value = "/getAreas", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public PlatformRes<List<Area>> getAreas() {
        return PlatformRes.success(areaService.getAreaByParentId("61def47fe91c40708f3b0d13a5fd9fb6"));
    }


    /**
     * 根据区域id获取当前区域的柜子信息
     *
     * @return
     */
    @RequestMapping(value = "/getCabinetByAreaId", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public PlatformRes<List<Cabinet>> getCabinetByAreaId(String areaId) {
        return PlatformRes.success(cabinetService.getCabinetByAreaId(areaId));
    }


    /**
     * 根据柜子id查询当前柜子的抽屉配置的商品信息
     *
     * @param cabinetId
     * @return
     */
    @RequestMapping(value = "/getSaleProductByCabinetId", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public PlatformRes<List<CabinetProductRelaction>> getSaleProductByCabinetId(String cabinetId) {
        return PlatformRes.success(cabinetProductRelactionDao.getSaleProductByCabinetId(cabinetId));
    }


    /**
     * 微信支付
     *
     * @param orderNo
     * @param openid
     * @param productIds
     * @param actualPayMoney
     * @param tradeType
     * @param remark
     * @return
     */
    @RequestMapping(value = "/wechatJsPay", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public PlatformRes<Map<String, String>> wechatJsPay(String orderNo, String openid, String productIds, Integer actualPayMoney, String tradeType, String remark) {
        return wechatPayService.wechatJsPay(orderNo, openid, productIds, actualPayMoney, tradeType, remark);
    }


    /**
     * 微信公众号取消订单
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "cancelOrder")
    @ResponseBody
    public PlatformRes<String> cancelOrder(String orderNo) {
        return orderService.cancelOrder(orderNo);
    }

}
