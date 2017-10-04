package com.thinkgem.jeesite.vo;

import java.util.Date;

public class AccessToken {
    private Integer id;

    private Integer tokenType;

    private String access_token;

    private Date inTime;

    private Date inOutTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTokenType() {
        return tokenType;
    }

    public void setTokenType(Integer tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getInOutTime() {
        return inOutTime;
    }

    public void setInOutTime(Date inOutTime) {
        this.inOutTime = inOutTime;
    }
}