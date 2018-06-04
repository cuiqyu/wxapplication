package com.thinkgem.jeesite.modules.wx.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.entity.FoodCategory;
import org.apache.ibatis.annotations.Param;

/**
 * 菜品分类DAO接口
 * @author tgp
 * @version 2018-06-04
 */
@MyBatisDao
public interface FoodCategoryDao extends CrudDao<FoodCategory> {

    FoodCategory getByName(@Param(value = "name") String name);

}