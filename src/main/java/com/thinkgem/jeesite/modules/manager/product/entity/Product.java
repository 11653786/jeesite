/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.product.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 商品实体类Entity
 * @author yt
 * @version 2017-08-19
 */
public class Product extends DataEntity<Product> {
	
	private static final long serialVersionUID = 1L;
	private String productName;		// 商品名称
	private Long productPrice;		// 商品价格
	private Long productActualPrice;		// 实际价格
	private String productStatus;		// 菜单状态
	private Date createTime;		// 创建时间
	private String remark;		// 菜单介绍
	private String imgurl;		// 产品主图
	
	public Product() {
		super();
	}

	public Product(String id){
		super(id);
	}

	@Length(min=1, max=255, message="商品名称长度必须介于 1 和 255 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@NotNull(message="商品价格不能为空")
	public Long getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Long productPrice) {
		this.productPrice = productPrice;
	}
	
	@NotNull(message="实际价格不能为空")
	public Long getProductActualPrice() {
		return productActualPrice;
	}

	public void setProductActualPrice(Long productActualPrice) {
		this.productActualPrice = productActualPrice;
	}
	
	@Length(min=1, max=11, message="菜单状态长度必须介于 1 和 11 之间")
	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=1, max=255, message="菜单介绍长度必须介于 1 和 255 之间")
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