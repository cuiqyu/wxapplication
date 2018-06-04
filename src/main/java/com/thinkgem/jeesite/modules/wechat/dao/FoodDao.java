package com.thinkgem.jeesite.modules.wechat.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wechat.entity.Food;

/**
 * 菜品dao
 */
@MyBatisDao
public interface FoodDao extends CrudDao<Food> {

}
