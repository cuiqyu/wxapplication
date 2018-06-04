/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 菜品Entity
 * @author tgp
 * @version 2018-06-04
 */
public class Food extends DataEntity<Food> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// type
	private String name;		// name
	private String picture;		// picture
	private String price;		// price
	private String recommend;		// recommend
	
	public Food() {
		super();
	}

	public Food(String id){
		super(id);
	}

	@Length(min=1, max=32, message="type长度必须介于 1 和 32 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Length(min=0, max=1, message="recommend长度必须介于 0 和 1 之间")
	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	
}