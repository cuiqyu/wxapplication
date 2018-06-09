/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 菜品分类Entity
 * @author tgp
 * @version 2018-06-04
 */
public class FoodCategory extends DataEntity<FoodCategory> {
    
    private static final long serialVersionUID = 1L;

    private String name; // 分类名称
    private Integer sort; // 排序
    private Date createAt; // 创建时间
    private Date updateAt; // 更新时间
    private String storeId; // 店铺id

    // 附加字段
    private boolean isShopowner; // 是否是店长
    
    public FoodCategory() {
        super();
    }

    public FoodCategory(String id){
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
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