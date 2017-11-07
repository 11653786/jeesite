package com.thinkgem.jeesite.api.entity.res;

/**
 * Created by Administrator on 2017/11/5.
 */
public class PaymentRes {

    private String cabinetNo;
    private String drawerNo;
    private String result;

    public PaymentRes() {
    }

    public PaymentRes(String cabinetNo, String drawerNo, String result) {
        this.cabinetNo = cabinetNo;
        this.drawerNo = drawerNo;
        this.result = result;
    }

    public String getCabinetNo() {
        return cabinetNo;
    }

    public void setCabinetNo(String cabinetNo) {
        this.cabinetNo = cabinetNo;
    }

    public String getDrawerNo() {
        return drawerNo;
    }

    public void setDrawerNo(String drawerNo) {
        this.drawerNo = drawerNo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
