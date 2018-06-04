package com.thinkgem.jeesite.modules.wechat.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wechat.entity.FoodCategory;

/**
 * 菜品dao
 *
 * @author tgp
 */
@MyBatisDao
public interface FoodCategoryDao extends CrudDao<FoodCategory> {
}
