/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.users.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 客户实体类Entity
 * @author yt
 * @version 2017-08-20
 */
public class Users extends DataEntity<Users> {
	
	private static final long serialVersionUID = 1L;
	private String nikeName;		// 微信昵称
	private String userName;		// 用户名
	private String phone;		// 手机号
	private String password;		// 密码
	private String userStatus;		// 登录状态
	private String openid;		// 微信标志
	private String alipayid;		// 支付宝标志
	private Integer integral;		// 积分
    private Integer saleTotalPrice; //消费总金额
	private Integer saleTotalNum;   //消费总数量
    private Date createTime;        //注册时间
    private Date wechatCancel;      //微信取关时间
	public Users() {
		super();
	}

	public Users(String id){
		super(id);
	}

	@Length(min=1, max=50, message="微信昵称长度必须介于 1 和 50 之间")
	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}
	
	@Length(min=0, max=255, message="用户名长度必须介于 0 和 255 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=1, max=20, message="手机号长度必须介于 1 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=1, max=50, message="密码长度必须介于 1 和 50 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=1, max=255, message="登录状态长度必须介于 1 和 255 之间")
	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
	@Length(min=0, max=255, message="微信标志长度必须介于 0 和 255 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=0, max=255, message="支付宝标志长度必须介于 0 和 255 之间")
	public String getAlipayid() {
		return alipayid;
	}

	public void setAlipayid(String alipayid) {
		this.alipayid = alipayid;
	}
	
	@NotNull(message="积分不能为空")
	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

    public Integer getSaleTotalPrice() {
        return saleTotalPrice;
    }

    public void setSaleTotalPrice(Integer saleTotalPrice) {
        this.saleTotalPrice = saleTotalPrice;
    }

    public Integer getSaleTotalNum() {
        return saleTotalNum;
    }

    public void setSaleTotalNum(Integer saleTotalNum) {
        this.saleTotalNum = saleTotalNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getWechatCancel() {
        return wechatCancel;
    }

    public void setWechatCancel(Date wechatCancel) {
        this.wechatCancel = wechatCancel;
    }
}