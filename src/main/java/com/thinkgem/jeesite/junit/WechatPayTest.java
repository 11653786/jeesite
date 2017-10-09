package com.thinkgem.jeesite.junit;


import com.thinkgem.jeesite.util.TenpayUtil;
import com.thinkgem.jeesite.util.WebRequestUtil;
import com.thinkgem.jeesite.util.XMLUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by erfeng on 17/8/17.
 */
public class WechatPayTest {


    public static void main(String[] args) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        //生成预支付请求参数列表
        //生成预支付请求参数列表

        String appId = "wx3bb5180e192011f3";
        String appsercet = "jzOJq3Y1oWkCSNbpT9WoKUGzNtBpoLVv";
        String mch_id = "1489914282";
        String out_trade_no = TenpayUtil.getCurrTime();
        String charSet = "utf-8";
        String signType = "MD5";
        String appkey = "jzOJq3Y1oWkCSNbpT9WoKUGzNtBpoLVv";


        params.put("appid", appId);
        params.put("mch_id", mch_id);
        params.put("nonce_str", TenpayUtil.genNonceStr());
        params.put("body", "测试订单" + Math.random());
        params.put("out_trade_no", out_trade_no);
        params.put("fee_type", "CNY");
        params.put("total_fee", 1 + "");
        params.put("spbill_create_ip", "127.0.0.1");
        params.put("trade_type", "NATIVE");
        params.put("product_id", 1 + "");
        params.put("notify_url", "http://115.28.145.123/wechatIntlSmNotify");
        String sign = TenpayUtil.createSign(params, charSet, signType, appkey).toUpperCase();
        params.put("sign", sign);
        boolean isTrue = TenpayUtil.isTenpaySign(params, charSet, signType, appkey);
        String body = XMLUtil.getXmlByMap(params);
        String result = null;
        result = WebRequestUtil.getResponseString("https://api.mch.weixin.qq.com/pay/unifiedorder", body, false);
        params = XMLUtil.doXMLParse(result);

        String prePayId = params.get("code_url");
        System.out.println(prePayId);


        //查询订单
        Map<String, String> params1 = new HashMap<String, String>();
        params1.put("appid", appId);
        params1.put("mch_id", mch_id);
        params1.put("nonce_str", TenpayUtil.genNonceStr());
        params1.put("out_trade_no", out_trade_no);
        String sign1 = TenpayUtil.createSign(params1, charSet, signType, appkey).toUpperCase();
        params1.put("sign", sign1);
        boolean isTrue1 = TenpayUtil.isTenpaySign(params1, charSet, signType, appkey);
        String body1 = XMLUtil.getXmlByMap(params1);
        String result1 = WebRequestUtil.getResponseString("https://api.mch.weixin.qq.com/pay/orderquery", body1, false);
        params1 = XMLUtil.doXMLParse(result1);
        System.out.println(result1);

        //查询退款订单订单
        Map<String, String> params2 = new HashMap<String, String>();
        params2.put("appid", appId);
        params2.put("mch_id", mch_id);
        params2.put("nonce_str", TenpayUtil.genNonceStr());
        params2.put("out_trade_no", "20170930141227");
        String sign2 = TenpayUtil.createSign(params2, charSet, signType, appkey).toUpperCase();
        params2.put("sign", sign2);
        String body2 = XMLUtil.getXmlByMap(params2);
        String result2 = WebRequestUtil.getResponseString("https://api.mch.weixin.qq.com/pay/refundquery", body2, false);
        params2 = XMLUtil.doXMLParse(result2);
        System.out.println(result2);


    }
}
