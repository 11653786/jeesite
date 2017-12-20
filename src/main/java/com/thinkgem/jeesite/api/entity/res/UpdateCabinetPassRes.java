package com.thinkgem.jeesite.api.entity.res;

/**
 * Created by Administrator on 2017/11/5.
 */
public class UpdateCabinetPassRes {

    private String foodPassword;
    private String sysPassword;
    private String result;

    public UpdateCabinetPassRes() {
    }

    public UpdateCabinetPassRes(String foodPassword, String sysPassword, String result) {
        this.foodPassword = foodPassword;
        this.sysPassword = sysPassword;
        this.result = result;
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


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
