package com.thinkgem.jeesite.modules.wx.rest;

import com.thinkgem.jeesite.modules.wx.entity.FoodComment;
import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.entity.vo.PostFoodCommentVo;
import com.thinkgem.jeesite.modules.wx.service.FoodCommentService;
import com.thinkgem.jeesite.modules.wx.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 提供给微信小程序的接口
 */
@RestController
@RequestMapping("/api/comment")
public class FoodCommentRest {

    @Autowired
    private FoodCommentService foodCommentService;

    /**
     * 对菜品进行评价
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public boolean foodComment(@RequestBody List<PostFoodCommentVo>  postFoodCommentVos) {
        return foodCommentService.foodComment(postFoodCommentVos);
    }

    /**
     * 根据菜品id获取评价列表
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<FoodComment> listFoodCommentByFoodId(@RequestParam("foodId") String foodId) {
        return foodCommentService.listFoodCommentByFoodId(foodId);
    }
}
