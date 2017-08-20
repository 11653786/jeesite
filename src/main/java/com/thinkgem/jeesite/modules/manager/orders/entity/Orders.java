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
 *
 * @author yt
 * @version 2017-08-19
 */
public class Orders extends DataEntity<Orders> {

    private static final long serialVersionUID = 1L;
    private String orderNo;        // 订单号
    private Long payMoney;        // 订单金额
    private Long actualPayMoney;        // 实际支付金额
    private String orderStatus;        // 订单状态
    private String wechatTradeNo;        // 微信流水
    private String alipayTradeNo;        // 支付宝流水
    private Date createTime;        // 创建时间
    private Date updateTime;        // 修改时间
    private String remark;        // 备注
    private Date paymentTime;        // 支付时间
    private String openid;        // 微信标志
    private String alipayid;        // alipay标志
    private Integer userId;        // 下单用户id
    private String phone;        // 手机号
    private String cabinetName;        // 柜子名称
    private String productId;        // 商品id
    private String productName;        // 商品名称
    private Long productMoney;        // 商品金额
    private Integer drawerNo;        // 抽屉编号
    private Date beginPaymentTime;        // 开始 支付时间
    private Date endPaymentTime;        // 结束 支付时间

    //	-----------红包补充字段
    private String redpacketId;
    private Long redpacketPrice;
    private String redpacketName;

    public Orders() {
        super();
    }

    public Orders(String id) {
        super(id);
    }

    @Length(min = 1, max = 40, message = "订单号长度必须介于 1 和 40 之间")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @NotNull(message = "订单金额不能为空")
    public Long getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Long payMoney) {
        this.payMoney = payMoney;
    }

    @NotNull(message = "实际支付金额不能为空")
    public Long getActualPayMoney() {
        return actualPayMoney;
    }

    public void setActualPayMoney(Long actualPayMoney) {
        this.actualPayMoney = actualPayMoney;
    }

    @Length(min = 1, max = 11, message = "订单状态长度必须介于 1 和 11 之间")
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Length(min = 0, max = 100, message = "微信流水长度必须介于 0 和 100 之间")
    public String getWechatTradeNo() {
        return wechatTradeNo;
    }

    public void setWechatTradeNo(String wechatTradeNo) {
        this.wechatTradeNo = wechatTradeNo;
    }

    @Length(min = 0, max = 100, message = "支付宝流水长度必须介于 0 和 100 之间")
    public String getAlipayTradeNo() {
        return alipayTradeNo;
    }

    public void setAlipayTradeNo(String alipayTradeNo) {
        this.alipayTradeNo = alipayTradeNo;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "创建时间不能为空")
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
    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    @Length(min = 0, max = 100, message = "微信标志长度必须介于 0 和 100 之间")
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Length(min = 0, max = 100, message = "alipay标志长度必须介于 0 和 100 之间")
    public String getAlipayid() {
        return alipayid;
    }

    public void setAlipayid(String alipayid) {
        this.alipayid = alipayid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Length(min = 0, max = 255, message = "手机号长度必须介于 0 和 255 之间")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min = 0, max = 40, message = "柜子名称长度必须介于 0 和 40 之间")
    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    @Length(min = 0, max = 40, message = "商品id长度必须介于 0 和 40 之间")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Length(min = 0, max = 50, message = "商品名称长度必须介于 0 和 50 之间")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductMoney() {
        return productMoney;
    }

    public void setProductMoney(Long productMoney) {
        this.productMoney = productMoney;
    }

    public Integer getDrawerNo() {
        return drawerNo;
    }

    public void setDrawerNo(Integer drawerNo) {
        this.drawerNo = drawerNo;
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


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRedpacketId() {
        return redpacketId;
    }

    public void setRedpacketId(String redpacketId) {
        this.redpacketId = redpacketId;
    }

    public Long getRedpacketPrice() {
        return redpacketPrice;
    }

    public void setRedpacketPrice(Long redpacketPrice) {
        this.redpacketPrice = redpacketPrice;
    }

    public String getRedpacketName() {
        return redpacketName;
    }

    public void setRedpacketName(String redpacketName) {
        this.redpacketName = redpacketName;
    }
}