package com.thinkgem.jeesite.api.enums;

public enum ResCodeMsgType {
    SUCCESS_("0", "成功"),
    PARAMS_NOT_EMPTY("101", "传入参数不能为空"),
    FAIL("501", "失败");

    private String code;
    private String desc;

    private ResCodeMsgType(String code, String desc) {
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
