/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.entity.ActionBaseDto;
import com.thinkgem.jeesite.modules.wx.entity.Store;
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

    @Autowired
    private StoreService storeService;

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

    @Transactional(readOnly = false)
    public ActionBaseDto updateById(Food food) {
        if (null == food || StringUtils.isEmpty(food.getId())) {
            logger.error("更新菜品信息失败，菜品id不能为空！");
            return ActionBaseDto.getFailedInstance("菜品id不能为空");
        }

        int result = foodDao.updateById(food);
        return (result == 1) ? ActionBaseDto.getSuccessInstance() : ActionBaseDto.getFailedInstance();
    }

    /**
     * 获取某一店铺的所有菜品
     * @return
     */
    public List<Food> listFood(String storeId) {
        if (StringUtils.isEmpty(storeId)) {
            throw new IllegalArgumentException("店铺id不可为空");
        }
        Store store = storeService.findStoreById(storeId);
        if (store == null) {
            throw new IllegalArgumentException("店铺不存在");
        }
        return foodDao.listAllFood(storeId);
    }

    /**
     * 获取商家推荐菜品
     */
    public List<Food> listSuggestFood(String storeId) {
        if (StringUtils.isEmpty(storeId)) {
            throw new IllegalArgumentException("店铺id不可为空");
        }
        Store store = storeService.findStoreById(storeId);
        if (store == null) {
            throw new IllegalArgumentException("店铺不存在");
        }
        return foodDao.listSuggestFood(storeId);
    }

    /**
     * 获取所有菜品
     */
    public List<Food> listAllFood(String storeId) {
        return foodDao.listAllFood(storeId);
    }
}