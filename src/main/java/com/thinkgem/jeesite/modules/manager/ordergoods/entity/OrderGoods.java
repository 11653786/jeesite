/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.ordergoods.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单详情Entity
 * @author yt
 * @version 2017-09-29
 */
public class OrderGoods extends DataEntity<OrderGoods> {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;		// 订单号
	private String productId;		// 商品id
	private String productName;		// 商品名称
	private Integer productActualPrice;		// 商品价格
	private Integer productNum;		// 商品数量
	private String areaId;		// 区域id
	private String areaName;		// 区域名称
	private String cabinetNo;		// 柜子编号
	private String drawerNo;		// 抽屉编号
	private Date createTime;		// 创建时间
	private Date updateTime;		// 修改时间
	private Date paymentTime;		// 支付时间
	
	public OrderGoods() {
		super();
	}

	public OrderGoods(String id){
		super(id);
	}

	@Length(min=1, max=50, message="订单号长度必须介于 1 和 50 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=0, max=40, message="商品id长度必须介于 0 和 40 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=1, max=100, message="商品名称长度必须介于 1 和 100 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@NotNull(message="商品价格不能为空")
	public Integer getProductActualPrice() {
		return productActualPrice;
	}

	public void setProductActualPrice(Integer productActualPrice) {
		this.productActualPrice = productActualPrice;
	}
	
	@NotNull(message="商品数量不能为空")
	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	
	@Length(min=0, max=50, message="区域id长度必须介于 0 和 50 之间")
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	@Length(min=0, max=50, message="区域名称长度必须介于 0 和 50 之间")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	@Length(min=0, max=50, message="柜子编号长度必须介于 0 和 50 之间")
	public String getCabinetNo() {
		return cabinetNo;
	}

	public void setCabinetNo(String cabinetNo) {
		this.cabinetNo = cabinetNo;
	}
	
	@Length(min=0, max=50, message="抽屉编号长度必须介于 0 和 50 之间")
	public String getDrawerNo() {
		return drawerNo;
	}

	public void setDrawerNo(String drawerNo) {
		this.drawerNo = drawerNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	
}