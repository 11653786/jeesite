package com.thinkgem.jeesite.api.enums;

public enum SocketResMsgType {
    WECHAT_PAYMENT_TYPE("0", "微信扫码付返回");


    private String code;
    private String desc;

    private SocketResMsgType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return this.code;
    }

    public String desc() {
        return this.desc;
    }

}
