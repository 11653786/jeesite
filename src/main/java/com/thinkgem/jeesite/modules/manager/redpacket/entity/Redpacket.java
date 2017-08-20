/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.redpacket.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 优惠卷实体类Entity
 * @author yt
 * @version 2017-08-20
 */
public class Redpacket extends DataEntity<Redpacket> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 优惠卷名称
	private Long redpacketPrice;		// 优惠金额
	private Long redpacketTotal;		// 优惠卷数量
	private String status;		// 优惠卷状态
	private Date createTime;		// 创建时间
	private String remark;		// remark
	private String redpacketType;		// 优惠卷类型
	private String discountRatio;		// 折扣比率
	private Integer limitDay;		// 过期天数
	
	public Redpacket() {
		super();
	}

	public Redpacket(String id){
		super(id);
	}

	@Length(min=1, max=255, message="优惠卷名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="优惠金额不能为空")
	public Long getRedpacketPrice() {
		return redpacketPrice;
	}

	public void setRedpacketPrice(Long redpacketPrice) {
		this.redpacketPrice = redpacketPrice;
	}
	
	@NotNull(message="优惠卷数量不能为空")
	public Long getRedpacketTotal() {
		return redpacketTotal;
	}

	public void setRedpacketTotal(Long redpacketTotal) {
		this.redpacketTotal = redpacketTotal;
	}
	
	@Length(min=1, max=11, message="优惠卷状态长度必须介于 1 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=255, message="remark长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=1, max=11, message="优惠卷类型长度必须介于 1 和 11 之间")
	public String getRedpacketType() {
		return redpacketType;
	}

	public void setRedpacketType(String redpacketType) {
		this.redpacketType = redpacketType;
	}
	
	@Length(min=0, max=100, message="折扣比率长度必须介于 0 和 100 之间")
	public String getDiscountRatio() {
		return discountRatio;
	}

	public void setDiscountRatio(String discountRatio) {
		this.discountRatio = discountRatio;
	}
	
	@NotNull(message="过期天数不能为空")
	public Integer getLimitDay() {
		return limitDay;
	}

	public void setLimitDay(Integer limitDay) {
		this.limitDay = limitDay;
	}
	
}