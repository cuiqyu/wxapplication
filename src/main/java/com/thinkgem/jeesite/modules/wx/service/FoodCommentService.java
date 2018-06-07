/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.wx.dao.FoodCommentDao;
import com.thinkgem.jeesite.modules.wx.entity.FoodComment;
import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.entity.vo.PostFoodCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺Service
 *
 * @author tgp
 * @version 2018-06-04
 */
@Service
public class FoodCommentService {

    @Autowired
    private FoodCommentDao foodCommentDao;

    /**
     * 对菜品进行评论
     * @param postFoodCommentVos
     * @return
     */
    public boolean foodComment(List<PostFoodCommentVo>  postFoodCommentVos) {
        for (PostFoodCommentVo postFoodCommentVo : postFoodCommentVos) {
            foodCommentDao.foodComment(postFoodCommentVo);
        }
        return true;
    }

    /**
     *
     */
    public List<FoodComment>  listFoodCommentByFoodId(String foodId) {
       return foodCommentDao.listFoodCommentByFoodId(foodId);
    }


}