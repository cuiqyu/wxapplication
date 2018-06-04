package com.thinkgem.jeesite.modules.wechat.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Objects;

/**
 * 菜品分类
 *
 * @author tgp
 */
public class FoodCategory extends DataEntity<FoodCategory> {

    private String id;
    private String name;

    public FoodCategory() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodCategory that = (FoodCategory) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "FoodCategory{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
