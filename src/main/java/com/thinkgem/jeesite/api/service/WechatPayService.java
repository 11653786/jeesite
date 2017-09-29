package com.thinkgem.jeesite.api.service;

import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.enums.ResCodeMsgType;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.config.WechatConfig;
import com.thinkgem.jeesite.util.TenpayUtil;
import com.thinkgem.jeesite.util.WebRequestUtil;
import com.thinkgem.jeesite.util.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangtao on 2017/8/18.
 */
@Service
public class WechatPayService {

    @Autowired
    private WechatConfig wechatConfig;


    /**
     * 微信统一下单
     *
     * @param orderNo
     * @param productId
     * @param productTotalPrice
     * @return
     */
    public PlatformRes<String> unifiedorder(String orderNo, String productId, Integer productTotalPrice, String tradeType) {
        Map<String, String> resultMap = null;
        String prePayId = null;
        String result = null;
        try {
            //xml格式字符串
            Map<String, String> params = setWechatConfig();

            params.put("nonce_str", TenpayUtil.genNonceStr());
            params.put("body", "测试订单" + Math.random());
            params.put("out_trade_no", orderNo);
            //货币类型
            params.put("fee_type", wechatConfig.fee_type);
            params.put("total_fee", productTotalPrice + "");
            params.put("spbill_create_ip", "127.0.0.1");
            params.put("trade_type", tradeType);
            params.put("product_id", productId);
            params.put("notify_url", wechatConfig.scan_pay_url);

            String sign = TenpayUtil.createSign(params, wechatConfig.charset, wechatConfig.signType, wechatConfig.app_key).toUpperCase();
            params.put("sign", sign);
            boolean isTrue = TenpayUtil.isTenpaySign(params, wechatConfig.charset, wechatConfig.signType, wechatConfig.app_key);
            if (isTrue) {
                String body = XMLUtil.getXmlByMap(params);
                result = WebRequestUtil.getResponseString(wechatConfig.unifiedorder_url, body, false);
                resultMap = XMLUtil.doXMLParse(result);
                //二维码图片
                prePayId = resultMap.get("code_url");
                //没有生成支付信息就返回微信给的信息
                if (StringUtils.isBlank(prePayId))
                    return PlatformRes.error(resultMap.get("return_code"), resultMap.get("return_msg"));
                else
                    return PlatformRes.success(prePayId);
            } else {
                return PlatformRes.error(ResCodeMsgType.WECHAT_SIGN_ERROR);
            }


        } catch (Exception e) {
            throw new RuntimeException("预下单失败: " + e.getMessage());
        }
    }


    /**
     * 微信预下单参数拼装,返回xml格式字符串
     *
     * @return
     */
    public Map<String, String> setWechatConfig() {
        Map<String, String> params = new HashMap<String, String>();
        //生成预支付请求参数列表
        params.put("appid", wechatConfig.app_id);
        //商户号
        params.put("mch_id", wechatConfig.mch_id);
        return params;


    }


}
