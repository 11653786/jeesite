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
@PropertySource({"classpath:alipay.properties"})
public class AlipayConfig {


    // 商户appid
    @Value("${APPID}")
    public String APPID;
    // 私钥 pkcs8格式的
    @Value("${RSA_PRIVATE_KEY}")
    public String RSA_PRIVATE_KEY;
    @Value("${notify_url}")
    public String notify_url;
    @Value("${return_url}")
    public String return_url;
    // 请求网关地址
    @Value("${URL}")
    public String URL;
    // 编码
    @Value("${CHARSET}")
    public String CHARSET;
    // 返回格式
    @Value("${FORMAT}")
    public String FORMAT;
    // 支付宝公钥
    @Value("${ALIPAY_PUBLIC_KEY}")
    public String ALIPAY_PUBLIC_KEY;
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    @Value("${SIGNTYPE}")
    public String SIGNTYPE;


}
