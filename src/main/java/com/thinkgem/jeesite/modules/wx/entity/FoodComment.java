package com.thinkgem.jeesite.modules.wx.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 客户 提交评论vo
 */
public class FoodComment extends DataEntity<FoodComment> {

    private static final long serialVersionUID = -1501734531203828472L;

    private String foodId;
    private String storeId;
    private String star;
    private String content;
    private String customerName;
    private String customerWxId;

    // 附加字段
    private boolean isShopowner; // 是否是店长
    private String storeName; // 店铺名称
    private String foodName; // 菜品名称

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

}
