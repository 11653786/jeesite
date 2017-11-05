package com.thinkgem.jeesite.api.entity.res;

/**
 * Created by Administrator on 2017/11/5.
 */
public class UpdateCabinetPassRes {

    private String code;
    private String message;
    private String data;
    private String foodPassword;
    private String sysPassword;

    public UpdateCabinetPassRes() {
    }

    public UpdateCabinetPassRes(String code, String message, String data, String foodPassword, String sysPassword) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.foodPassword = foodPassword;
        this.sysPassword = sysPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFoodPassword() {
        return foodPassword;
    }

    public void setFoodPassword(String foodPassword) {
        this.foodPassword = foodPassword;
    }

    public String getSysPassword() {
        return sysPassword;
    }

    public void setSysPassword(String sysPassword) {
        this.sysPassword = sysPassword;
    }
}
