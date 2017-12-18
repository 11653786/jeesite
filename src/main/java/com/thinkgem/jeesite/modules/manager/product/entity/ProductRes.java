/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 商品实体类Entity
 * @author yt
 * @version 2017-08-19
 */
public class ProductRes {

	private static final long serialVersionUID = 1L;
	private String productName;		// 商品名称
//	private Integer productPrice;		// 商品价格
	private Integer productActualPrice;		// 实际价格
//	private String productStatus;		// 菜单状态
//	private Date createTime;		// 创建时间
	private String remark;		// 菜单介绍
	private String imgurl;		// 产品主图

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

//	public Integer getProductPrice() {
//		return productPrice;
//	}
//
//	public void setProductPrice(Integer productPrice) {
//		this.productPrice = productPrice;
//	}

	public Integer getProductActualPrice() {
		return productActualPrice;
	}

	public void setProductActualPrice(Integer productActualPrice) {
		this.productActualPrice = productActualPrice;
	}

//	public String getProductStatus() {
//		return productStatus;
//	}
//
//	public void setProductStatus(String productStatus) {
//		this.productStatus = productStatus;
//	}

//	public Date getCreateTime() {
//		return createTime;
//	}
//
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
}