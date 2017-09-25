package com.thinkgem.jeesite.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by yangtao on 2017/9/25.
 */
@Component
@Configuration
@PropertySource({"classpath:cabinet.properties"})
public class CabinetConfig {


    @Value("${isTipCabinetFull}")
    public boolean isTipCabinetFull;
    @Value("${cabinetFullNum}")
    public Integer cabinetFullNum;

    @Value("${isTipCabinetReplace}")
    public boolean isTipCabinetReplace;
    @Value("${cabinetReplaceNum}")
    public Integer cabinetReplaceNum;


}
