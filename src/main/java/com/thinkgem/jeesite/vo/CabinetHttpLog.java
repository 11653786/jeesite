package com.thinkgem.jeesite.vo;

import java.util.Date;

public class CabinetHttpLog {
    private Integer id;

    private String cabinetno;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCabinetno() {
        return cabinetno;
    }

    public void setCabinetno(String cabinetno) {
        this.cabinetno = cabinetno == null ? null : cabinetno.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}