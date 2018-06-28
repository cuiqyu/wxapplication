/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wx.constant.OrderState;
import com.thinkgem.jeesite.modules.wx.entity.*;
import com.thinkgem.jeesite.modules.wx.entity.vo.OrderVo;
import com.thinkgem.jeesite.modules.wx.utils.HttpUtils;
import com.thinkgem.jeesite.modules.wx.utils.MD5Util;
import com.thinkgem.jeesite.modules.wx.utils.JsonUtils;
import com.thinkgem.jeesite.modules.wx.dao.OrderDao;
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

import static com.thinkgem.jeesite.modules.wx.constant.WechatConstant.*;

/**
 * 菜品Service
 *
 * @author tgp
 * @version 2018-06-04
 */
@Service
public class OrderService extends CrudService<OrderDao, Order> {

    private final static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private FoodService foodService;

    @Autowired
    private StoreService storeService;

    /**
     * 创建订单
     *
     * @param postOrder
     * @return
     */
    @Transactional
    public OrderVo addOrder(PostOrder postOrder) {
        //查找本店库里所有菜品id
        List<Food> foodList = foodService.listAllFood(postOrder.getStoreId());
        //用户点菜菜品id和数量map
        Map<String, Integer> foodMap = postOrder.getFoodMap();
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
                    amount += (food.getPrice().doubleValue()) * count;
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
        order.setState(OrderState.UNPAID);

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
        String foodDetail = JsonUtils.List2Str(order2Foods);
        order.setFoodDetail(foodDetail);


        //----------------------------调用微信登录凭证校验---------------------------
//        //https://developers.weixin.qq.com/miniprogram/dev/api/api-login.html#wxloginobject
//        String code = "071KYi5t01zdId1TrP1t0zL55t0KYi5T";
//        PostWxAuth postWxAuth = new PostWxAuth(code);
//        String string = "https://api.weixin.qq.com/sns/jscode2session";
//        String url = string + "?" + "appid=" + postWxAuth.getAppid()
//            + "&" + "secret=" + postWxAuth.getSecret() + "&" + "js_code=" + postWxAuth.getJs_code()
//            + "&" + "grant_type=" + postWxAuth.getGrant_type();
//        String aa = HttpUtils.excute(url);
//        System.out.println(aa);
//        return aa;


        String openId = postOrder.getCustomerWxId();
        int totalFee = (int) (amount * 10 * 10);
        //签名
        String stringA =
            "appid=" + appid +
                "&" + "body=" + body +
                "&" + "mch_id=" + mch_id +
                "&" + "nonce_str=" + nonce_str +
                "&" + "notify_url=" + notify_url +
                "&" + "openid=" + openId +
                "&" + "out_trade_no=" + id +
                "&" + "spbill_create_ip=" + spbill_create_ip +
                "&" + "total_fee=" + totalFee +
                "&" + "trade_type=" + trade_type;

        String SignTemp = stringA + "&key=" + key;
        System.out.println(SignTemp);
        String sign = MD5Util.md5(SignTemp).toUpperCase();
        //-----------------------------调用微信统一下单api----------------------------
        //https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
        PostWxOrder postWxOrder = new PostWxOrder(sign, id, totalFee, openId);
        String x = HttpUtils.post("https://api.mch.weixin.qq.com/pay/unifiedorder", postWxOrder);
        OrderVo vo = HttpUtils.xmlToBean(OrderVo.class, x);
        if ("SUCCESS".equals(vo.getReturn_code()) && "SUCCESS".equals(vo.getResult_code())) {
            orderDao.addOrder(order);
        }
        return vo;

        //3. 增加菜品的销量

//
//        return true;

    }

    /**
     * 根据点餐的用户wx_id获取订单记录
     *
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


    /**
     * 更新订单状态为已支付
     *
     * @return
     */
    public OrderDetail findById(String orderId) {
        return orderDao.findById(orderId);
    }


    /**
     * 更新订单状态为已支付
     *
     * @return
     */
    public void updateOrderState(String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            throw new IllegalArgumentException("orderId不可为空");
        }
        OrderDetail orderDetail = findById(orderId);
        if (orderDetail == null) {
            throw new IllegalArgumentException("订单不存在");
        }
        if (OrderState.UNPAID.name().equals(orderDetail.getOrderState())) {
            orderDao.updateState(orderDetail.getId());
        }
    }


}