package com.thinkgem.jeesite.modules.wx.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.entity.Food;

import java.util.List;

/**
 * 菜品DAO接口
 * @author tgp
 * @version 2018-06-04
 */
@MyBatisDao
public interface FoodDao extends CrudDao<Food> {

    List<Food> listFoodByCategoryId(String categoryId);

    int updateById(Food food);

    List<Food> listSuggestFood();
}