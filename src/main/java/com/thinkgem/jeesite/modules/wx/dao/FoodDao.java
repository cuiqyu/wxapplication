/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.entity.Food;

/**
 * 菜品DAO接口
 * @author tgp
 * @version 2018-06-04
 */
@MyBatisDao
public interface FoodDao extends CrudDao<Food> {

    int updateById(Food food);

}