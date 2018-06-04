package com.thinkgem.jeesite.modules.wechat.service.impl;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wechat.dao.FoodCategoryDao;
import com.thinkgem.jeesite.modules.wechat.entity.FoodCategory;
import com.thinkgem.jeesite.modules.wechat.service.FoodService;
import org.springframework.stereotype.Service;

/**
 * 菜品分类服务实现
 */
@Service
public class FoodCategoryServiceImpl extends CrudService<FoodCategoryDao, FoodCategory> implements FoodService {

}
