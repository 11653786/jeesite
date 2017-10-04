package com.thinkgem.jeesite.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource({"classpath:wechat.properties"})
public class WechatConfig {
    @Value("${app_key}")
    public String app_key;
    @Value("${app_id}")
    public String app_id;
    @Value("${app_sercet}")
    public String app_sercet;
    @Value("${mch_id}")
    public String mch_id;
    @Value("${fee_type}")
    public String fee_type;
    @Value("${trade_type}")
    public String trade_type;
    //微信扫码付回调url
    @Value("${scan_pay_url}")
    public String scan_pay_url;
    @Value("${charset}")
    public String charset;
    @Value("${signType}")
    public String signType;
    @Value("${unifiedorder_url}")
    public String unifiedorder_url;
    @Value("${query_order_url}")
    public String query_order_url;
    @Value("${refund_order_url}")
    public String refund_order_url;
    //退款证书所在地址
    @Value("${refund_token}")
    public String refund_token;
    //微信退款订单查询
    @Value("${refund_order_query}")
    public String refund_order_query;
    //微信公众号请求获取tokenurl
    @Value("${token_url}")
    public String token_url;


}
