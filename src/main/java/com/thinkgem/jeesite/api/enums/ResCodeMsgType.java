package com.thinkgem.jeesite.api.enums;

public enum ResCodeMsgType {
    SUCCESS_("0", "成功"),
    PARAMS_NOT_EMPTY("101", "传入参数不能为空"),
    PASSWORD_NOT_ONE("102", "至少传入一个密码"),
    CABINET_NOT_EXISTS("103", "柜子不存在"),
    WECHAT_SIGN_ERROR("104", "非法提交,验证签名错误"),
    PRODUCT_NOT_EXISTS("105", "商品信息不存在"),
    PRODUCT_NOT_USE("106", "无效商品"),
    CABINET_DRAWER_EXISTS("107","柜子或抽屉信息不存在"),
    DRAWER_NOT_ACTION("108","当前抽屉不可操作"),
    DRAWER_NOT_PUT_FOOD("109","当前抽屉没有放餐或者已被支付锁定"),
    DRAWER_NOT_PUT_PRODUCT("110","当前抽屉不可放当前商品"),
    PRODUCT_NUM_NOT_EMPTY("111","商品数量不能为空"),
    ORDERS_NOT_EXISTS("111","订单不存在"),
    REFUND_ORDERS_NOT_EXISTS("112","用户未发起退款或已成功退款!"),
    REFUND_ERROR("113","退款失败"),
    OUT_FOOD_EXCEPTION("114","订单信息异常，子订单没有商品"),
    PUT_ORDER_MESSAGE_EXCEPTION("115","订单已经取餐或者未支付"),
    DRAWER_CABINET_NOT_EMPTY("116","抽屉柜子不能为空"),
    PUT_FOOD_PASS_ERROR("117","取餐密码不正确"),
    ORDER_NOT_OUT("118","当前订单不可取餐"),
    HTTP_LOG_ERROR("119","通信操作失败"),
    DRAWER_HAS_FOOD("120","当前抽屉已经放餐"),
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
