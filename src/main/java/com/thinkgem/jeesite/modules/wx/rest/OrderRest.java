package com.thinkgem.jeesite.modules.wx.rest;

import com.thinkgem.jeesite.modules.wx.entity.vo.OrderDetail;
import com.thinkgem.jeesite.modules.wx.entity.vo.PostOrder;
import com.thinkgem.jeesite.modules.wx.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 提供给微信小程序的接口
 */
@RestController
@RequestMapping("/api/order")
public class OrderRest {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean addOrder(@RequestBody PostOrder postOrder) {
        return orderService.addOrder(postOrder);
    }

    /**
     * 根据点餐的用户wx_id获取订单记录
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<OrderDetail> findOrderByWx_id(@RequestParam("storeId") String storeId,
                                              @RequestParam("wxId") String wxId) {
        return orderService.findOrderByWx_id(storeId, wxId);
    }
}
