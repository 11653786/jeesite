/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinet.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 快餐柜实体类Entity
 * @author yt
 * @version 2017-08-19
 */
public class Cabinet extends DataEntity<Cabinet> {
	
	private static final long serialVersionUID = 1L;
	private String cabinetName;		// 柜子名称
	private String province;		// 省
	private String city;		// 市
	private String area;		// 区
	private String address;		// 详细地址
	private String cabinetStatus;		// 柜子状态
	private Date createTime;		// 创建时间
	private String cabinetNos;		// 柜子编号
	private List<Drawer> drawerList = Lists.newArrayList();		// 子表列表

	private String province1;
	private String city1;
	private String area1;

	//帮助字段
	private Integer total;

	public Cabinet() {
		super();
	}

	public Cabinet(String id){
		super(id);
	}

	@Length(min=1, max=50, message="柜子名称长度必须介于 1 和 50 之间")
	public String getCabinetName() {
		return cabinetName;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}
	
	@Length(min=1, max=40, message="省长度必须介于 1 和 40 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=1, max=40, message="市长度必须介于 1 和 40 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=1, max=255, message="区长度必须介于 1 和 255 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=1, max=255, message="详细地址长度必须介于 1 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=1, max=11, message="柜子状态长度必须介于 1 和 11 之间")
	public String getCabinetStatus() {
		return cabinetStatus;
	}

	public void setCabinetStatus(String cabinetStatus) {
		this.cabinetStatus = cabinetStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=1, max=50, message="柜子编号长度必须介于 1 和 50 之间")
	public String getCabinetNos() {
		return cabinetNos;
	}

	public void setCabinetNos(String cabinetNos) {
		this.cabinetNos = cabinetNos;
	}
	
	public List<Drawer> getDrawerList() {
		return drawerList;
	}

	public void setDrawerList(List<Drawer> drawerList) {
		this.drawerList = drawerList;
	}

	public String getProvince1() {
		return province1;
	}

	public void setProvince1(String province1) {
		this.province1 = province1;
	}

	public String getCity1() {
		return city1;
	}

	public void setCity1(String city1) {
		this.city1 = city1;
	}

	public String getArea1() {
		return area1;
	}

	public void setArea1(String area1) {
		this.area1 = area1;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}