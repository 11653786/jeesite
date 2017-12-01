package com.thinkgem.jeesite.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 支付寶配置信息
 * Created by yangtao on 2017/8/18.
 */
@Component
@Configuration
@PropertySource({"classpath:zhifubao.properties"})
public class AlipayConfig {


    // 商户appid
    @Value("${appid}")
    public String appid;
    // 商户appid
    @Value("${pid}")
    public String pid;
    // 私钥 pkcs8格式的
    @Value("${private_key}")
    public String private_key;
    //商户公钥
    @Value("${public_key}")
    public String public_key;

    @Value("${notify_url}")
    public String notify_url;
    // 请求网关地址
    @Value("${URL}")
    public String URL;
    // 支付宝公钥
    @Value("${alipay_public_key}")
    public String alipay_public_key;
    @Value("${sign_type}")
    public String sign_type;
    @Value("${charset}")
    public String charset;


}
