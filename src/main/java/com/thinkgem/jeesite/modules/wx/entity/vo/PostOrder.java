package com.thinkgem.jeesite.modules.wx.entity.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 订单提交vo
 */
public class PostOrder implements Serializable {

    private String storeId;
    private String customerName;
    private String customerWxId;
    private Map<String,Integer> foodMap;

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

    public Map<String, Integer> getFoodMap() {
        return foodMap;
    }

    public void setFoodMap(Map<String, Integer> foodMap) {
        this.foodMap = foodMap;
    }
}
