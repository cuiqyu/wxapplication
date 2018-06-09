package com.thinkgem.jeesite.modules.wx.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * 菜品Entity
 * @author tgp
 * @version 2018-06-04
 */
public class Food extends DataEntity<Food> {
    
    private static final long serialVersionUID = 1L;
    private String categoryId; // 分类id
    private String name; // 菜品名称
    private String picture; // 图片
    private BigDecimal price; // 价格
    private Boolean recommend; // 是否推荐
    private Boolean state; // 上架状态
    private int sale; // 销量
    private String unit; // 单位
    private String storeId; // 店铺id

    // 附加字段
    private String categoryName; // 分类名称
    private boolean isShopowner; // 是否是店长
    
    public Food() {
        super();
    }

    public Food(String id){
        super(id);
    }

    @Length(min=1, max=32, message="categoryId长度必须介于 1 和 32 之间")
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Length(min=1, max=64, message="name长度必须介于 1 和 64 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min=1, max=128, message="picture长度必须介于 1 和 128 之间")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Length(min=0, max=1, message="recommend长度必须介于 0 和 1 之间")
    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    @Length(min=0, max=1, message="state长度必须介于 0 和 1 之间")
    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public boolean getIsShopowner() {
        return isShopowner;
    }

    public void setIsShopowner(boolean shopowner) {
        isShopowner = shopowner;
    }

}