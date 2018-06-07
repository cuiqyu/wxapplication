package com.thinkgem.jeesite.modules.wx.entity;

import com.thinkgem.jeesite.modules.wx.constant.OrderState;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 */
public class Order implements Serializable {
    private String id;
    private Double amount;
    private OrderState state;
    private String customerName;
    private String customerWxId;
    private String storeId;
    private Date createAt;
    private Date createDay;
    private Date createMonth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerWxId() {
        return customerWxId;
    }

    public void setCustomerWxId(String customerWxId) {
        this.customerWxId = customerWxId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Date createDay) {
        this.createDay = createDay;
    }

    public Date getCreateMonth() {
        return createMonth;
    }

    public void setCreateMonth(Date createMonth) {
        this.createMonth = createMonth;
    }
}
