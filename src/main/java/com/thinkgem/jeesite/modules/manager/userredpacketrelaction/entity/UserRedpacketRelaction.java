/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.userredpacketrelaction.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户红包关系实体类Entity
 * @author yt
 * @version 2017-08-20
 */
public class UserRedpacketRelaction extends DataEntity<UserRedpacketRelaction> {
	
	private static final long serialVersionUID = 1L;
	private String redpacketId;		// 优惠卷id
	private String redpacketName;		// 优惠卷名称
	private String userid;		// 领取用户
	private String userName;		// 用户名称
	private Long redpacketPrice;		// 优惠金额
	private Date createTime;		// 领取时间
	private String redpacketType;		// 红包类型
	private String discountRatio;		// 折扣比例
	private Date beginCreateTime;		// 开始 领取时间
	private Date endCreateTime;		// 结束 领取时间

	private Integer inUse;		//是否使用
	
	public UserRedpacketRelaction() {
		super();
	}

	public UserRedpacketRelaction(String id){
		super(id);
	}

	@Length(min=0, max=255, message="优惠卷id长度必须介于 0 和 255 之间")
	public String getRedpacketId() {
		return redpacketId;
	}

	public void setRedpacketId(String redpacketId) {
		this.redpacketId = redpacketId;
	}
	
	@Length(min=1, max=255, message="优惠卷名称长度必须介于 1 和 255 之间")
	public String getRedpacketName() {
		return redpacketName;
	}

	public void setRedpacketName(String redpacketName) {
		this.redpacketName = redpacketName;
	}
	
	@NotNull(message="领取用户不能为空")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Length(min=0, max=255, message="用户名称长度必须介于 0 和 255 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Long getRedpacketPrice() {
		return redpacketPrice;
	}

	public void setRedpacketPrice(Long redpacketPrice) {
		this.redpacketPrice = redpacketPrice;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=1, max=11, message="红包类型长度必须介于 1 和 11 之间")
	public String getRedpacketType() {
		return redpacketType;
	}

	public void setRedpacketType(String redpacketType) {
		this.redpacketType = redpacketType;
	}
	
	@Length(min=0, max=255, message="折扣比例长度必须介于 0 和 255 之间")
	public String getDiscountRatio() {
		return discountRatio;
	}

	public void setDiscountRatio(String discountRatio) {
		this.discountRatio = discountRatio;
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

	public Integer getInUse() {
		return inUse;
	}

	public void setInUse(Integer inUse) {
		this.inUse = inUse;
	}
}