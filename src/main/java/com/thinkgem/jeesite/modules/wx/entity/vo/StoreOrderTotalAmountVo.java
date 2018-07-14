package com.thinkgem.jeesite.modules.wx.entity.vo;

import com.thinkgem.jeesite.modules.wx.constant.OrderState;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 店铺营业总额
 *
 * @auther cuiqiongyu
 * @create 2018/7/14 15:52
 */
public class StoreOrderTotalAmountVo implements Serializable {

    private static final long serialVersionUID = 4198631275821709523L;

    private Double totalAmount; // 总营业额
    private OrderState state; // 订单的状态
    private String storeId; // 店铺id
    private Integer number; // 日或者月

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
