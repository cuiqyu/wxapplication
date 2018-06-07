package com.thinkgem.jeesite.modules.wx.dao;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.entity.Order;
import com.thinkgem.jeesite.modules.wx.entity.vo.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 菜品DAO接口
 * @author tgp
 * @version 2018-06-04
 */
@MyBatisDao
public interface OrderDao{

    boolean addOrder(Order order);

    List<OrderDetail> findOrderByWx_id(@Param("storeId") String storeId, @Param("wxId") String wxId);

}