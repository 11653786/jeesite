/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.orders.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单实体类Entity
 * @author yt
 * @version 2017-08-16
 */
public class Orders extends DataEntity<Orders> {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;		// 订单号
	private Double payMoney;		// 订单金额
	private Double actualPayMoney;		// 实际支付金额
	private Integer orderStatus;		// 订单状态
	private String wechatTradeNo;		// 微信流水
	private String alipayTradeNo;		// 支付宝流水
	private Date createTime;		// 创建时间
	private Date updateTime;		// 修改时间
	private String remark;		// 备注
	private Date paymentTime;		// 支付时间
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private Date beginPaymentTime;		// 开始 支付时间
	private Date endPaymentTime;		// 结束 支付时间
	
	public Orders() {
		super();
	}

	public Orders(String id){
		super(id);
	}

	@Length(min=1, max=40, message="订单号长度必须介于 1 和 40 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@NotNull(message="订单金额不能为空")
	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	
	@NotNull(message="实际支付金额不能为空")
	public Double getActualPayMoney() {
		return actualPayMoney;
	}

	public void setActualPayMoney(Double actualPayMoney) {
		this.actualPayMoney = actualPayMoney;
	}
	
	@NotNull(message="订单状态不能为空")
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	@Length(min=0, max=100, message="微信流水长度必须介于 0 和 100 之间")
	public String getWechatTradeNo() {
		return wechatTradeNo;
	}

	public void setWechatTradeNo(String wechatTradeNo) {
		this.wechatTradeNo = wechatTradeNo;
	}
	
	@Length(min=0, max=100, message="支付宝流水长度必须介于 0 和 100 之间")
	public String getAlipayTradeNo() {
		return alipayTradeNo;
	}

	public void setAlipayTradeNo(String alipayTradeNo) {
		this.alipayTradeNo = alipayTradeNo;
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
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="支付时间不能为空")
	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	
	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
		
	public Date getBeginPaymentTime() {
		return beginPaymentTime;
	}

	public void setBeginPaymentTime(Date beginPaymentTime) {
		this.beginPaymentTime = beginPaymentTime;
	}
	
	public Date getEndPaymentTime() {
		return endPaymentTime;
	}

	public void setEndPaymentTime(Date endPaymentTime) {
		this.endPaymentTime = endPaymentTime;
	}
		
}