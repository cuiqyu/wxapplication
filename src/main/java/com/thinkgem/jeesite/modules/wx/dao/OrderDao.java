package com.thinkgem.jeesite.modules.wx.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.entity.Order;
import com.thinkgem.jeesite.modules.wx.entity.vo.OrderDetail;
import com.thinkgem.jeesite.modules.wx.entity.vo.OrderListVo;
import com.thinkgem.jeesite.modules.wx.entity.vo.StoreOrderTotalAmountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 菜品DAO接口
 * @author tgp
 * @version 2018-06-04
 */
@MyBatisDao
public interface OrderDao extends CrudDao<Order> {

    int addOrder(Order order);

    List<OrderDetail> findOrderByWx_id(@Param("storeId") String storeId, @Param("wxId") String wxId, @Param("pageSize") Integer pageSize, @Param("pageNo") Integer pageNo);

    OrderDetail findById(@Param("orderId") String orderId);

    int updateState(@Param("id") String id);

    List<StoreOrderTotalAmountVo> findStoreTotalAmount(Order order);

    List<StoreOrderTotalAmountVo> findStoreTotalDetailAmount(Order order);

}