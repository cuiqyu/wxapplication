package com.thinkgem.jeesite.modules.wx.rest;

import com.thinkgem.jeesite.modules.wx.entity.Food;
import com.thinkgem.jeesite.modules.wx.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 提供给微信小程序的接口
 */
@RestController
@RequestMapping("/api/food")
public class FoodRest {

    @Autowired
    private FoodService foodService;

    /**
     * 获取某一分类下的菜品
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<Food> listFoodByCategoryId(@RequestParam("categoryId") String categoryId) {
        return foodService.listFoodByCategoryId(categoryId);
    }

    /**
     * 获取商家推荐菜品
     */
    @RequestMapping(value = "suggest", method = RequestMethod.GET)
    public List<Food> listSuggestFood() {
        return foodService.listSuggestFood();
    }
}
