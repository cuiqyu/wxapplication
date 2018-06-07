/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import com.thinkgem.jeesite.modules.wx.dao.OrderDao;
import com.thinkgem.jeesite.modules.wx.entity.Food;
import com.thinkgem.jeesite.modules.wx.entity.Order;
import com.thinkgem.jeesite.modules.wx.entity.Order2Food;
import com.thinkgem.jeesite.modules.wx.entity.vo.OrderVo;
import com.thinkgem.jeesite.modules.wx.entity.vo.PostOrder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 菜品Service
 *
 * @author tgp
 * @version 2018-06-04
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private FoodService foodService;

    /**
     * 创建订单
     * @param postOrder
     * @return
     */
    @Transactional
    public boolean addOrder(PostOrder postOrder) {
        //校验订单
        List<Food> foodList = foodService.listAllFood(postOrder.getStoreId());
        List<OrderVo> orderVoList = postOrder.getOrderVoList();
        List<String> foodIds = new ArrayList<>();
        for (OrderVo orderVo : orderVoList) {
            foodIds.add(orderVo.getId());
        }


        List<Food> list2 = new ArrayList<>();
        int tag = foodIds.size();
        double amount = 0;

        for (String id : foodIds) {
            for (Food food : foodList) {
                if (food.getId().equals(id)) {
                    list2.add(food);
                    tag--;
                    amount += food.getPrice().doubleValue();
                    break;
                }
            }
        }
        if (tag != 0) {
            throw new IllegalArgumentException("存在不存在的菜品,下单失败");
        }



        //1.创建订单
        Order order = new Order();
        String id = UUID.randomUUID().toString();//??????
        order.setId(id);
        order.setAmount(amount);
        order.setCreateAt(new Date());
        DateTime currentDate = new DateTime();
        Date date1 = currentDate.withTimeAtStartOfDay().toDate();
        order.setCreateDay(date1);
        Date date2 = currentDate.dayOfMonth().withMinimumValue().toDate();
        order.setCreateMonth(date2);
        order.setCustomerName(postOrder.getCustomerName());
        order.setCustomerWxId(postOrder.getCustomerWxId());
        order.setStoreId(postOrder.getStoreId());
        orderDao.addOrder(order);


        //2.创建订单详情
        List<Order2Food> order2Foods = new ArrayList<>();
        for (Food food : list2) {
            Order2Food order2Food = new Order2Food();
            order2Food.setOrderId(id);
            order2Food.setFoodId(food.getId());
            order2Food.setFoodCount(1);
            order2Food.setFoodCategoryId(food.getCategoryId());
            order2Food.setFoodCategoryName(food.getCategoryName());
            order2Food.setFoodPrice(food.getPrice().doubleValue());
            order2Foods.add(order2Food);
        }
        //orderDao.addOrder2Foods(order2Foods);


        //3. 增加菜品的销量


        return true;

    }

}