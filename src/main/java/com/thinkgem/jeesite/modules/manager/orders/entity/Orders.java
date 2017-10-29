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
    private Integer payMoney;        // 订单金额
    private Integer actualPayMoney;        // 实际支付金额
    private Integer orderStatus;        // 订单状态
    private Integer paymentStatus;         //支付状态:0,微信扫码支付,1微信公众号支付,2支付宝支付
    private String cabinetNo;               //柜子编号
    private String wechatTradeNo;        // 微信流水
    private String alipayTradeNo;        // 支付宝流水
    private Date createTime;        // 创建时间
    private Date updateTime;        // 修改时间
    private String remark;        // 备注
    private Date paymentTime;        // 支付时间
    private String openid;        // 微信标志
    private String alipayid;        // alipay标志
    private String userId;        // 下单用户id
    private String phone;        // 手机号
    private Date beginPaymentTime;        // 开始 支付时间
    private Date endPaymentTime;        // 结束 支付时间
    private String refundNo;            //退款订单号
    private Integer refundStatus;       //退款状态:0,未退款,1退款成功,2退款失败
    private Date refundTime;            //退款时间


    //	-----------红包补充字段
    private String redpacketId;
    private Integer redpacketPrice;
    private String redpacketName;

    //
    private String putPassword; //取餐密码

    //后台退款订单查询使用
    private String payOrderStatus;

    //后台退款订单查询使用
    private String refundOrderStatus;

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
    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    @NotNull(message = "实际支付金额不能为空")
    public Integer getActualPayMoney() {
        return actualPayMoney;
    }

    public void setActualPayMoney(Integer actualPayMoney) {
        this.actualPayMoney = actualPayMoney;
    }

    @NotNull(message = "订单状态不能为空")
    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Length(min = 0, max = 255, message = "手机号长度必须介于 0 和 255 之间")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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



    public String getRedpacketId() {
        return redpacketId;
    }

    public void setRedpacketId(String redpacketId) {
        this.redpacketId = redpacketId;
    }

    public Integer getRedpacketPrice() {
        return redpacketPrice;
    }

    public void setRedpacketPrice(Integer redpacketPrice) {
        this.redpacketPrice = redpacketPrice;
    }

    public String getRedpacketName() {
        return redpacketName;
    }

    public void setRedpacketName(String redpacketName) {
        this.redpacketName = redpacketName;
    }


    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public String getCabinetNo() {
        return cabinetNo;
    }

    public void setCabinetNo(String cabinetNo) {
        this.cabinetNo = cabinetNo;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public String getPutPassword() {
        return putPassword;
    }

    public void setPutPassword(String putPassword) {
        this.putPassword = putPassword;
    }

    public String getPayOrderStatus() {
        return payOrderStatus;
    }

    public void setPayOrderStatus(String payOrderStatus) {
        this.payOrderStatus = payOrderStatus;
    }


    public String getRefundOrderStatus() {
        return refundOrderStatus;
    }

    public void setRefundOrderStatus(String refundOrderStatus) {
        this.refundOrderStatus = refundOrderStatus;
    }
}