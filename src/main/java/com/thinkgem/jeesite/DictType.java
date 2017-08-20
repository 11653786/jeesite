package com.thinkgem.jeesite;

/**
 * Created by erfeng on 17/8/20.
 */
public enum DictType {

    cabinet_product_status_up("cabinet_product_status", "1","商品配置状态其用"),
    user_status_limit_login("user_status","0","用户限制登录");

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
