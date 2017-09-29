package com.thinkgem.jeesite.api.service;

import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.enums.ResCodeMsgType;
import com.thinkgem.jeesite.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yangtao on 2017/9/29.
 */
@Service
public class OrderService {


    @Autowired
    private WechatPayService wechatPayService;


    /**
     * @param orderNo
     * @param productId
     * @param productPrice
     * @param orderType
     * @param tradeType
     * @return
     */
    public PlatformRes<String> preorder(String orderNo, String productId, Integer productPrice, String orderType, String tradeType) {

        //参数验证
        if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(productId) || productPrice == null || StringUtils.isBlank(orderNo))
            return PlatformRes.error(ResCodeMsgType.PARAMS_NOT_EMPTY);


        //这里要创建订单,订单状态为0,等到回调通过以后更改状态,微信预约下单生成二维码
        if (orderType.equals("0")) {
            PlatformRes<String> wechatPayResult = wechatPayService.unifiedorder(orderNo, productId, productPrice, tradeType);
            return wechatPayResult;
        } else
            //支付宝预约下单生成二维码
            return null;


    }

}
