/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import com.thinkgem.jeesite.modules.wx.dao.StoreDao;
import com.thinkgem.jeesite.modules.wx.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 店铺Service
 *
 * @author tgp
 * @version 2018-06-04
 */
@Service
public class StoreService {

    @Autowired
    private StoreDao storeDao;

    /**
     * 获取所有的店铺
     * 后台使用机构作为店铺，返回给小程序的是机构的code和name。其他表关联的都是机构的code
     */
    public List<Store> listAllStore() {
        return storeDao.listAllStore();
    }

    /**
     * 根据id获取店铺
     * 后台使用机构作为店铺，返回给小程序的是机构的code和name。其他表关联的都是机构的code
     */
    public Store findStoreById(String storeId) {
        return storeDao.findStoreById(storeId);
    }

}