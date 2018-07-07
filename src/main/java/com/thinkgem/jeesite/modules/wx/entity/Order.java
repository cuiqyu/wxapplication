package com.thinkgem.jeesite.modules.wx.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.wx.constant.OrderState;

import java.util.Date;
import java.util.List;

/**
 * 订单
 */
public class Order extends DataEntity<Order> {

    private static final long serialVersionUID = 7899609229905952726L;

    private String id;
    private Double amount;
    private OrderState state;
    private String customerName;
    private String customerWxId;
    private String storeId;
    private Date createAt;
    private Date createDay;
    private Date createMonth;
    private String foodDetail;

    // 附加字段
    private boolean isShopowner; // 是否是店长
    private String storeName; // 店铺名称
    private String orderTimeType; // 订单时间查询方式
    private List<Order2Food> foodDetailInfoList; // 订单详情信息
    private Date createYear;

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

    public String getFoodDetail() {
        return foodDetail;
    }

    public void setFoodDetail(String foodDetail) {
        this.foodDetail = foodDetail;
    }

    public boolean getIsShopowner() {
        return isShopowner;
    }

    public void setIsShopowner(boolean shopowner) {
        isShopowner = shopowner;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getOrderTimeType() {
        return orderTimeType;
    }

    public void setOrderTimeType(String orderTimeType) {
        this.orderTimeType = orderTimeType;
    }

    public List<Order2Food> getFoodDetailInfoList() {
        return foodDetailInfoList;
    }

    public void setFoodDetailInfoList(List<Order2Food> foodDetailInfoList) {
        this.foodDetailInfoList = foodDetailInfoList;
    }

    public Date getCreateYear() {
        return createYear;
    }

    public void setCreateYear(Date createYear) {
        this.createYear = createYear;
    }

}
