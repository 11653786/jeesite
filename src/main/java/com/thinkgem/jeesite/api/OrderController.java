package com.thinkgem.jeesite.api;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.api.entity.req.PreOrderReq;
import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yangtao on 2017/8/18.
 */
@Controller
@RequestMapping(value = "/api/order")
public class OrderController {


    @Autowired
    private OrderService orderService;


    /**
     * localhost:8080/api/order/preorder?productId=1&productPrice=300&paymentType=0
     * 微信预下单生成二维码
     * <p/>
     * {
     * "code": "0",
     * "message": "成功",
     * "data": "weixin://wxpay/bizpayurl?pr=hMnBust"
     * }
     *
     * @param productsStr    商品信息
     * @param paymentType 支付类型: 0,微信扫码支付 1,微信公众号支付 2,支付宝
     * @param repackgeId  红包id,公众号支付有用
     * @return
     */
    @RequestMapping(value = "/preorder", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public PlatformRes<String> preorder(String productsStr, Integer paymentType, String repackgeId) {
        String tradeType = null;
        List<PreOrderReq> products = JSONObject.parseArray(productsStr, PreOrderReq.class);

        if (paymentType == 0) {
            tradeType = "NATIVE";
        }
        return orderService.preorder(products, paymentType, tradeType, repackgeId);
    }


}
