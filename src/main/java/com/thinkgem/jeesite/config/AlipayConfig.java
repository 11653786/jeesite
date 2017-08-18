package com.thinkgem.jeesite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by yangtao on 2017/8/18.
 */
@Component
@Configuration
@PropertySource({"classpath:alipay.properties"})
public class AlipayConfig {
}
