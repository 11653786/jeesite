package com.thinkgem.jeesite.api.entity.res;

/**
 * Created by Administrator on 2017/11/5.
 */
public class PaymentRes {

    private String cabinetNo;
    private String drawerNo;
    private String data;

    public PaymentRes() {
    }

    public PaymentRes(String cabinetNo, String drawerNo, String data) {
        this.cabinetNo = cabinetNo;
        this.drawerNo = drawerNo;
        this.data = data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
