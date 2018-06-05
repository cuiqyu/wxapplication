package com.thinkgem.jeesite.modules.wx.rest;


import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.wx.entity.FoodCategory;
import com.thinkgem.jeesite.modules.wx.service.FoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 提供给微信小程序的接口
 *
 * @author tgp
 * @version 2018-06-04
 */
@RestController
@RequestMapping("/api/category/")
public class FoodCategoryRest extends BaseController {

    @Autowired
    private FoodCategoryService foodCategoryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<FoodCategory> listAllFoodCategory() {
        return foodCategoryService.listAllFoodCategory();
    }

}