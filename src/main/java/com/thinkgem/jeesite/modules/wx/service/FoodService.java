/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.entity.ActionBaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wx.entity.Food;
import com.thinkgem.jeesite.modules.wx.dao.FoodDao;

/**
 * 菜品Service
 *
 * @author tgp
 * @version 2018-06-04
 */
@Service
@Transactional(readOnly = true)
public class FoodService extends CrudService<FoodDao, Food> {

    @Autowired
    private FoodDao foodDao;

    public Food get(String id) {
        return super.get(id);
    }

    public List<Food> findList(Food food) {
        return super.findList(food);
    }

    public Page<Food> findPage(Page<Food> page, Food food) {
        return super.findPage(page, food);
    }

    @Transactional(readOnly = false)
    public void save(Food food) {
        super.save(food);
    }

    @Transactional(readOnly = false)
    public void delete(Food food) {
        super.delete(food);
    }

    public ActionBaseDto updateById(Food food) {
        if (null == food || StringUtils.isEmpty(food.getId())) {
            logger.error("更新菜品信息失败，菜品id不能为空！");
            return ActionBaseDto.getFailedInstance("菜品id不能为空");
        }

        int result = foodDao.updateById(food);
        return (result == 1) ? ActionBaseDto.getSuccessInstance() : ActionBaseDto.getFailedInstance();
    }

    public List<Food> listFoodByCategoryId(String categoryId) {
        if (StringUtils.isEmpty(categoryId)) {
            throw new IllegalArgumentException("分类id不可为空");
        }
        return foodDao.listFoodByCategoryId(categoryId);
    }

}