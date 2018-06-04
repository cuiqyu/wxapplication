package com.thinkgem.jeesite.modules.wx.service;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FoodCategoryDao foodCategoryDao;

    public FoodCategory get(String id) {
        return super.get(id);
    }

    public FoodCategory getByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        FoodCategory foodCategory = foodCategoryDao.getByName(name);
        return foodCategory;
    }

    public List<FoodCategory> findList(FoodCategory foodCategory) {
        return super.findList(foodCategory);
    }
    
    public Page<FoodCategory> findPage(Page<FoodCategory> page, FoodCategory foodCategory) {
        return super.findPage(page, foodCategory);
    }
    
    @Transactional(readOnly = false)
    public void save(FoodCategory foodCategory) {
        if (StringUtils.isEmpty(foodCategory.getId())) {
            foodCategory.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            foodCategoryDao.insert(foodCategory);
        }
        foodCategoryDao.update(foodCategory);
    }
    
    @Transactional(readOnly = false)
    public void delete(FoodCategory foodCategory) {
        super.delete(foodCategory);
    }

}