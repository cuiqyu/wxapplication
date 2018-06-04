/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 菜品分类Entity
 * @author tgp
 * @version 2018-06-04
 */
public class FoodCategory extends DataEntity<FoodCategory> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	
	public FoodCategory() {
		super();
	}

	public FoodCategory(String id){
		super(id);
	}

	@Length(min=1, max=64, message="name长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}