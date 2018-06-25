package com.thinkgem.jeesite.modules.wx.dao;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.entity.FoodComment;
import com.thinkgem.jeesite.modules.wx.entity.Order;
import com.thinkgem.jeesite.modules.wx.entity.vo.OrderDetail;
import com.thinkgem.jeesite.modules.wx.entity.vo.PostFoodCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 菜品评论DAO接口
 * @author tgp
 * @version 2018-06-04
 */
@MyBatisDao
public interface FoodCommentDao {

    boolean foodComment(PostFoodCommentVo postFoodCommentVo);

    List<FoodComment> listFoodCommentByFoodId(@Param("foodId") String foodId, @Param("pageSize") Integer pageSize, @Param("pageNo") Integer pageNo);

}