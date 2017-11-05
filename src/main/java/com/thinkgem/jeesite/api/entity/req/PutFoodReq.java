package com.thinkgem.jeesite.api.entity.req;

/**
 * Created by Administrator on 2017/11/5.
 */
public class PutFoodReq {

    private String cabinetNo;
    private String drawerNo;
    private String productId;
    private String foodPassword;


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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getFoodPassword() {
        return foodPassword;
    }

    public void setFoodPassword(String foodPassword) {
        this.foodPassword = foodPassword;
    }
}
