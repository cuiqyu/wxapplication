/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wx.dao.FoodCommentDao;
import com.thinkgem.jeesite.modules.wx.dao.StoreDao;
import com.thinkgem.jeesite.modules.wx.entity.FoodComment;
import com.thinkgem.jeesite.modules.wx.entity.vo.PostFoodCommentVo;
import com.thinkgem.jeesite.modules.wx.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * 评论Service
 *
 * @author tgp
 * @version 2018-06-04
 */
@Service
public class FoodCommentService extends CrudService<FoodCommentDao, FoodComment> {

    private final static Logger logger = LoggerFactory.getLogger(FoodCommentService.class);

    @Autowired
    private FoodCommentDao foodCommentDao;

    /**
     * 对菜品进行评论
     * @param postFoodCommentVos
     * @return
     */
    @Transactional(readOnly = false)
    public boolean foodComment(List<PostFoodCommentVo>  postFoodCommentVos) {
        for (PostFoodCommentVo postFoodCommentVo : postFoodCommentVos) {
            postFoodCommentVo.setId(UUIDUtils.timeBasedStr());
            foodCommentDao.foodComment(postFoodCommentVo);
        }
        return true;
    }

    /**
     * 根据菜品id获取评论
     */
    public List<FoodComment> listFoodCommentByFoodId(String foodId, Integer pageSize, Integer pageNo) {
        if ((null != pageSize && pageSize <= 0) || (null != pageNo && pageNo <= 0)) {
            logger.info("分页查询菜品评价信息失败，pageSize和pageNo都不能小于1！");
            return new LinkedList<>();
        }
        int limit = pageSize;
        int offset = pageNo == 1 ? 0 : pageSize * (pageNo - 1);
       return foodCommentDao.listFoodCommentByFoodId(foodId, limit, offset);
    }

    /**
     * 分页查询评价信息
     * @param page 分页对象
     * @param foodComment
     * @return
     */
    public Page<FoodComment> findPage(Page<FoodComment> page, FoodComment foodComment) {
        return super.findPage(page, foodComment);
    }

}