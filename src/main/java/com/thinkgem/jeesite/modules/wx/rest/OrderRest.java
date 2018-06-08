package com.thinkgem.jeesite.modules.wx.rest;

import com.thinkgem.jeesite.modules.wx.entity.vo.PostOrder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供给微信小程序的接口
 */
@RestController
@RequestMapping("/api/order")
public class OrderRest {

    /**
     * 创建订单
     */
    public boolean addOrder(@RequestBody PostOrder postOrder) {
        return true;
    }
}
