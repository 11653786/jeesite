/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 柜子商品关系配置Entity
 * @author yt
 * @version 2017-08-19
 */
public class CabinetProductRelactionRes {

	private static final long serialVersionUID = 1L;
//	private String cabinetId;		// 柜子id
	private String cabinetNo;		// 柜子编号
//    private String drawerId;       //柜子编号
    private String drawerNo;       //柜子
	private String productId;		// 商品id
//	private String cabinetName;		// 柜子名称
	private String productName;		// 商品名称
//	private String cabinetProductStatus;		// 启用状态
//	private Date createTime;		// 创建时间


	private String foodStatus;		//放餐状态

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFoodStatus() {
		return foodStatus;
	}

	public void setFoodStatus(String foodStatus) {
		this.foodStatus = foodStatus;
	}
}