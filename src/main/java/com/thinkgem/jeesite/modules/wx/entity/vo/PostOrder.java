package com.thinkgem.jeesite.modules.wx.entity.vo;

import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.List;

/**
 * 订单提交vo
 */
public class PostOrder implements Serializable {

    private String storeId;
    private String customerName;
    private String customerWxId;
    private List<OrderVo> orderVoList;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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

    public List<OrderVo> getOrderVoList() {
        return orderVoList;
    }

    public void setOrderVoList(List<OrderVo> orderVoList) {
        this.orderVoList = orderVoList;
    }
}
