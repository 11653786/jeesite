package com.thinkgem.jeesite;

/**
 * Created by erfeng on 17/8/20.
 */
public enum DictType {

    cabinet_product_status_up("cabinet_product_status", "1","商品配置状态其用"),
    user_status_limit_login("user_status","0","用户限制登录"),
    user_status_login("user_status","1","用户登录"),
    dict_redpacket_in_use("in_use","1","红包未使用"),
    dict_redpacket_in_not_use("in_use","2","红包已使用"),
    redpacket_status_use("redpacket_status","1","优惠卷状态,可用");

    private String type;
    private String value;
    private  String desc;

    DictType(String type, String value, String desc) {
        this.type = type;
        this.value = value;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
