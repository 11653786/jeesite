package com.thinkgem.jeesite.api.enums;

public enum ResCodeMsgType {
    SUCCESS_("0", "成功"),
    PARAMS_NOT_EMPTY("101", "传入参数不能为空"),
    PASSWORD_NOT_ONE("102", "至少传入一个密码"),
    CABINET_NOT_EXISTS("103", "柜子不存在"),
    WECHAT_SIGN_ERROR("104", "非法提交,验证签名错误"),
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
