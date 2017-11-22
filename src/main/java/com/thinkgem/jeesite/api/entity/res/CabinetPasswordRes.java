package com.thinkgem.jeesite.api.entity.res;

/**
 * Created by Administrator on 2017/11/22.
 */
public class CabinetPasswordRes {

    private String sysPassword;
    private String foodPassword;
    private String cabinetNo;

    public String getSysPassword() {
        return sysPassword;
    }

    public void setSysPassword(String sysPassword) {
        this.sysPassword = sysPassword;
    }

    public String getFoodPassword() {
        return foodPassword;
    }

    public void setFoodPassword(String foodPassword) {
        this.foodPassword = foodPassword;
    }

    public String getCabinetNo() {
        return cabinetNo;
    }

    public void setCabinetNo(String cabinetNo) {
        this.cabinetNo = cabinetNo;
    }
}
