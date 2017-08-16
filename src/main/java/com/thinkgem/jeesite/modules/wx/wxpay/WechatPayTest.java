package com.thinkgem.jeesite.modules.wx.wxpay;



import com.thinkgem.jeesite.modules.wx.wxpay.base.util.tenpay.util.WebRequestUtil;
import com.thinkgem.jeesite.modules.wx.wxpay.base.util.tenpay.util.WechatUtil;
import com.thinkgem.jeesite.modules.wx.wxpay.base.util.tenpay.util.XMLUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by erfeng on 17/8/17.
 */
public class WechatPayTest {
    public static void main(String[] args) throws  Exception {
        Map<String, String> params = new HashMap<String, String>();
        //生成预支付请求参数列表
        params.put("appid","wx70375dde1ac8a3d0");
        params.put("mch_id","1393701402");
        params.put("nonce_str", TenpayUtil.genNonceStr());
        params.put("body","测试订单"+Math.random());
        params.put("out_trade_no",TenpayUtil.genNonceStr());
        params.put("fee_type", "CNY");
        params.put("total_fee", 1+"");
        params.put("spbill_create_ip", "127.0.0.1");
        params.put("trade_type", "NATIVE");
        params.put("product_id",1+"");
        params.put("notify_url","http://115.28.145.123/wechatIntlSmNotify");
        String sign = TenpayUtil.createSign(params, "utf-8", "MD5", "BCFB58527EF47F485BE308C0331A86B9").toUpperCase();
        params.put("sign", sign);
        String body = XMLUtil.getXmlByMap(params);
        String result = null;
        result = WebRequestUtil.getResponseString("https://api.mch.weixin.qq.com/pay/unifiedorder", body, false);
        params = XMLUtil.doXMLParse(result);
        String prePayId = params.get("code_url");
        System.out.println(prePayId);
    }
}
