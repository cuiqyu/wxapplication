package com.thinkgem.jeesite.modules.wechat.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;
import java.util.Objects;

/**
 * 菜品
 *
 * @author tgp
 */
public class Food extends DataEntity<Food> {

    //菜品id
    private String id;
    //菜品分类
    private String type;
    //菜品名称
    private String name;
    //菜品图片
    private String pictrue;
    //菜品价格
    private Double price;
    //是否推荐菜品
    private boolean recommend;
    //菜品图片
    private Date createAt;
    //菜品图片
    private Date updateAt;

    public Food() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictrue() {
        return pictrue;
    }

    public void setPictrue(String pictrue) {
        this.pictrue = pictrue;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food commodity = (Food) o;
        return recommend == commodity.recommend &&
            Objects.equals(id, commodity.id) &&
            Objects.equals(type, commodity.type) &&
            Objects.equals(name, commodity.name) &&
            Objects.equals(pictrue, commodity.pictrue) &&
            Objects.equals(price, commodity.price) &&
            Objects.equals(createAt, commodity.createAt) &&
            Objects.equals(updateAt, commodity.updateAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, name, pictrue, price, recommend, createAt, updateAt);
    }

    @Override
    public String toString() {
        return "Food{" +
            "id='" + id + '\'' +
            ", type='" + type + '\'' +
            ", name='" + name + '\'' +
            ", pictrue='" + pictrue + '\'' +
            ", price=" + price +
            ", recommend=" + recommend +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
            '}';
    }
}
