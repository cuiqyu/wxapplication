package com.thinkgem.jeesite.modules.wechat.service.impl;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wechat.dao.FoodDao;
import com.thinkgem.jeesite.modules.wechat.entity.Food;
import com.thinkgem.jeesite.modules.wechat.service.FoodCategoryService;
import org.springframework.stereotype.Service;

/**
 * 菜品服务实现
 */
@Service
public class FoodServiceImpl extends CrudService<FoodDao, Food> implements FoodCategoryService {

}
