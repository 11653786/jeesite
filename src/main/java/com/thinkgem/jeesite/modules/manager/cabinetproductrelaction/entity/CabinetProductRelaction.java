/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 柜子商品关系配置Entity
 * @author yt
 * @version 2017-08-19
 */
public class CabinetProductRelaction extends DataEntity<CabinetProductRelaction> {
	
	private static final long serialVersionUID = 1L;
	private String cabinetId;		// 柜子id
	private String cabinetNo;		// 柜子编号
	private String productId;		// 商品id
	private String cabinetName;		// 柜子名称
	private String productName;		// 商品名称
	private String cabinetProductStatus;		// 启用状态
	private Date createTime;		// 创建时间
	
	public CabinetProductRelaction() {
		super();
	}

	public CabinetProductRelaction(String id){
		super(id);
	}

	@Length(min=0, max=40, message="柜子id长度必须介于 0 和 40 之间")
	public String getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}
	
	@Length(min=0, max=40, message="柜子编号长度必须介于 0 和 40 之间")
	public String getCabinetNo() {
		return cabinetNo;
	}

	public void setCabinetNo(String cabinetNo) {
		this.cabinetNo = cabinetNo;
	}
	
	@Length(min=0, max=40, message="商品id长度必须介于 0 和 40 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=40, message="柜子名称长度必须介于 0 和 40 之间")
	public String getCabinetName() {
		return cabinetName;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}
	
	@Length(min=0, max=40, message="商品名称长度必须介于 0 和 40 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=11, message="启用状态长度必须介于 0 和 11 之间")
	public String getCabinetProductStatus() {
		return cabinetProductStatus;
	}

	public void setCabinetProductStatus(String cabinetProductStatus) {
		this.cabinetProductStatus = cabinetProductStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}