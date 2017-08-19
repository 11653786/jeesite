/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinet.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 快餐柜实体类Entity
 * @author yt
 * @version 2017-08-19
 */
public class Drawer extends DataEntity<Drawer> {
	
	private static final long serialVersionUID = 1L;
	private String drawerNo;		// 抽屉编号
	private Cabinet cabinet;		// 柜子id 父类
	private String cabinetId;
	private String cabinetName;		// 柜子名称
	private String drawerStatus;		// 抽屉状态
	private String foodStatus;		// 放餐状态
	private Date createTime;		// 创建时间
	private String productId;		//放入柜中的商品时间
	private String productName;	//放入柜中的商品名称
	private Date inTime;	//放入时间

	private String provinceId;
	private String cabinetProvinceName;	//柜子所在省

	private String cityId;
	private String cabinetCityName;	//柜子所在城市

	private String areaId;	//区id
	private String cabinetAreaName; //柜子所在区域

	public Drawer() {
		super();
	}

	public Drawer(String id){
		super(id);
	}

	public Drawer(Cabinet cabinet){
		this.cabinet = cabinet;
	}

	@Length(min=1, max=11, message="抽屉编号长度必须介于 1 和 11 之间")
	public String getDrawerNo() {
		return drawerNo;
	}

	public void setDrawerNo(String drawerNo) {
		this.drawerNo = drawerNo;
	}
	
	@Length(min=1, max=40, message="柜子id长度必须介于 1 和 40 之间")
	public String getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}
	
	@Length(min=0, max=50, message="柜子名称长度必须介于 0 和 50 之间")
	public String getCabinetName() {
		return cabinetName;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}
	
	@Length(min=1, max=11, message="抽屉状态长度必须介于 1 和 11 之间")
	public String getDrawerStatus() {
		return drawerStatus;
	}

	public void setDrawerStatus(String drawerStatus) {
		this.drawerStatus = drawerStatus;
	}
	
	@Length(min=1, max=11, message="放餐状态长度必须介于 1 和 11 之间")
	public String getFoodStatus() {
		return foodStatus;
	}

	public void setFoodStatus(String foodStatus) {
		this.foodStatus = foodStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Cabinet getCabinet() {
		return cabinet;
	}

	public void setCabinet(Cabinet cabinet) {
		this.cabinet = cabinet;
	}


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCabinetProvinceName() {
		return cabinetProvinceName;
	}

	public void setCabinetProvinceName(String cabinetProvinceName) {
		this.cabinetProvinceName = cabinetProvinceName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCabinetCityName() {
		return cabinetCityName;
	}

	public void setCabinetCityName(String cabinetCityName) {
		this.cabinetCityName = cabinetCityName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCabinetAreaName() {
		return cabinetAreaName;
	}

	public void setCabinetAreaName(String cabinetAreaName) {
		this.cabinetAreaName = cabinetAreaName;
	}
}