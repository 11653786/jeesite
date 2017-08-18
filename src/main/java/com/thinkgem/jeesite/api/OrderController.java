package com.thinkgem.jeesite.api;

import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yangtao on 2017/8/18.
 */
@Controller
@RequestMapping(value = "/api/order")
public class OrderController {


    @Autowired
    private PayService payService;


    /**
     * 微信预下单生成二维码
     *
     * @param orderNo      订单号
     * @param productId    商品id
     * @param productPrice 商品金额
     * @param orderType 订单类型 0,微信,1支付宝
     * @return
     */
    @RequestMapping(value = "/preorder", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public PlatformRes<String> preorder(String orderNo, String productId, Integer productPrice, String orderType) {
        return payService.preorder(orderNo, productId, productPrice,orderType);
    }


}
