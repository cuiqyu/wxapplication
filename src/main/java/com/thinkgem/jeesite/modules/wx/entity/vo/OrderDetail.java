package com.thinkgem.jeesite.modules.wx.entity.vo;

import java.util.Date;

/**
 * 返回给微信小程序某一个用户的订单详情
 */
public class OrderDetail {

    private String id;
    private Double amount;
    private String storeId;
    private Date createAt;
    private String order2FoodList;

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

    public String getOrder2FoodList() {
        return order2FoodList;
    }

    public void setOrder2FoodList(String order2FoodList) {
        this.order2FoodList = order2FoodList;
    }
}
