package com.thinkgem.jeesite.api.entity.handler;

/**
 * Created by yangtao on 2017/9/29.
 */
public class CabinerDrawerHandler {


    private String cabinetNo;
    private String cabinetName;
    private String areaName;
    private String areaId;
    private String drawerNo;
    private String drawerStatus;
    private String oodStatus;

    public String getCabinetNo() {
        return cabinetNo;
    }

    public void setCabinetNo(String cabinetNo) {
        this.cabinetNo = cabinetNo;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    public String getDrawerNo() {
        return drawerNo;
    }

    public void setDrawerNo(String drawerNo) {
        this.drawerNo = drawerNo;
    }

    public String getDrawerStatus() {
        return drawerStatus;
    }

    public void setDrawerStatus(String drawerStatus) {
        this.drawerStatus = drawerStatus;
    }

    public String getOodStatus() {
        return oodStatus;
    }

    public void setOodStatus(String oodStatus) {
        this.oodStatus = oodStatus;
    }


    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
