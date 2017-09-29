package com.thinkgem.jeesite.api.entity.req;

import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;

/**
 * Created by yangtao on 2017/9/29.
 */
public class PreOrderReq {
    //商品id
    private String productId;
    //柜子编号
    private String cabinetNo;
    //抽屉编号
    private String drawerNo;

    //这三个参数不用传递,是半路拼接上用来下单的
    private String areaId;
    //区域名称
    private String areaName;
    //商品价格
    private Integer getProductActualPrice;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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


    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getGetProductActualPrice() {
        return getProductActualPrice;
    }

    public void setGetProductActualPrice(Integer getProductActualPrice) {
        this.getProductActualPrice = getProductActualPrice;
    }
}
