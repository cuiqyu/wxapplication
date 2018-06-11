package com.thinkgem.jeesite.modules.wx.rest;

import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 提供给微信小程序的接口
 */
@RestController
@RequestMapping("/api/store")
public class StoreRest {

    @Autowired
    private StoreService storeService;

    /**
     * 获取所有店铺
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<Store> listAllStore() {
        return storeService.listAllStore();
    }
}
