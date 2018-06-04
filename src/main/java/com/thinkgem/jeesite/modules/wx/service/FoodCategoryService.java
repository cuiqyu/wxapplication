/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wx.entity.FoodCategory;
import com.thinkgem.jeesite.modules.wx.dao.FoodCategoryDao;

/**
 * 菜品分类Service
 * @author tgp
 * @version 2018-06-04
 */
@Service
@Transactional(readOnly = true)
public class FoodCategoryService extends CrudService<FoodCategoryDao, FoodCategory> {

	public FoodCategory get(String id) {
		return super.get(id);
	}
	
	public List<FoodCategory> findList(FoodCategory foodCategory) {
		return super.findList(foodCategory);
	}
	
	public Page<FoodCategory> findPage(Page<FoodCategory> page, FoodCategory foodCategory) {
		return super.findPage(page, foodCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(FoodCategory foodCategory) {
		super.save(foodCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(FoodCategory foodCategory) {
		super.delete(foodCategory);
	}
	
}