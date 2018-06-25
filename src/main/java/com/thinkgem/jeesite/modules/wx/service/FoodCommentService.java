/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import com.thinkgem.jeesite.modules.wx.dao.FoodCommentDao;
import com.thinkgem.jeesite.modules.wx.entity.FoodComment;
import com.thinkgem.jeesite.modules.wx.entity.vo.PostFoodCommentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * 评论Service
 *
 * @author tgp
 * @version 2018-06-04
 */
@Service
public class FoodCommentService {

    private final static Logger logger = LoggerFactory.getLogger(FoodCommentService.class);

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
     * 根据菜品id获取评论
     */
    public List<FoodComment>  listFoodCommentByFoodId(String foodId, Integer pageSize, Integer pageNo) {
        if ((null != pageSize && pageSize < 0) || (null != pageNo && pageNo < 0)) {
            logger.info("分页查询菜品评价信息失败，pageSize和pageNo都不能小于0！");
            return new LinkedList<>();
        }
       return foodCommentDao.listFoodCommentByFoodId(foodId, pageSize, pageNo);
    }


}