/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.utils.SerializationDefine;
import com.thinkgem.jeesite.modules.wx.dao.OrderDao;
import com.thinkgem.jeesite.modules.wx.entity.Food;
import com.thinkgem.jeesite.modules.wx.entity.Order;
import com.thinkgem.jeesite.modules.wx.entity.Order2Food;
import com.thinkgem.jeesite.modules.wx.entity.vo.OrderDetail;
import com.thinkgem.jeesite.modules.wx.entity.vo.PostOrder;
import com.thinkgem.jeesite.modules.wx.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 菜品Service
 *
 * @author tgp
 * @version 2018-06-04
 */
@Service
public class OrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private FoodService foodService;

    @Autowired
    private StoreService storeService;

    /**
     * 创建订单
     * @param postOrder
     * @return
     */
    @Transactional
    public boolean addOrder(PostOrder postOrder) {
        //查找本店库里所有菜品id
        List<Food> foodList = foodService.listAllFood(postOrder.getStoreId());
        //用户点菜菜品id和数量map
        Map<String,Integer> foodMap = postOrder.getFoodMap();
        //用户点菜菜品id列表
        Set<String> foodIds = foodMap.keySet();
        //存放用户点菜对应的菜品信息
        List<Food> list2 = new ArrayList<>();
        //用户提交的菜品id数量
        int tag = foodIds.size();
        //用户点菜的总金额
        double amount = 0;

        //找到用户提交的id对应的菜品信息
        for (String id : foodIds) {
            for (Food food : foodList) {
                if (food.getId().equals(id)) {
                    list2.add(food);
                    //用户提交的菜品id数量，找到一个就减去一个
                    tag--;
                    //累计金额
                    int count = foodMap.get(food.getId());
                    amount += (food.getPrice().doubleValue())  * count;
                    break;
                }
            }
        }
        //用户提交的菜品id数量，存在没有找到的菜品，下单失败
        if (tag != 0) {
            throw new IllegalArgumentException("存在不存在的菜品,下单失败");
        }



        //1.创建订单
        Order order = new Order();
        String id = UUIDUtils.timeBasedStr();
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

        //2.创建订单详情
        List<Order2Food> order2Foods = new ArrayList<>();
        for (Food food : list2) {
            Order2Food order2Food = new Order2Food();
            order2Food.setOrderId(id);
            order2Food.setFoodId(food.getId());
            order2Food.setFoodCount(foodMap.get(food.getId()));
            order2Food.setFoodCategoryId(food.getCategoryId());
            order2Food.setFoodCategoryName(food.getCategoryName());
            order2Food.setFoodPrice(food.getPrice().doubleValue());
            order2Foods.add(order2Food);
        }
        String foodDetail  = SerializationDefine.List2Str(order2Foods);
        order.setFoodDetail(foodDetail);
        orderDao.addOrder(order);


        //3. 增加菜品的销量



        return true;

    }

    /**
     * 根据点餐的用户wx_id获取订单记录
     * @return
     */
    public List<OrderDetail> findOrderByWx_id(String storeId, String wxId, Integer pageSize, Integer pageNo) {
        if (StringUtils.isEmpty(storeId)) {
            throw new IllegalArgumentException("店铺id不可为空");
        }
        if ((null != pageSize && pageSize < 0) || (null != pageNo && pageNo < 0)) {
            logger.info("分页查询用户订单信息失败，pageSize和pageNo都不能小于0！");
            return new LinkedList<>();
        }
        Store store = storeService.findStoreById(storeId);
        if (store == null) {
            throw new IllegalArgumentException("店铺不存在");
        }
        return orderDao.findOrderByWx_id(storeId, wxId, pageSize, pageNo);
    }

}