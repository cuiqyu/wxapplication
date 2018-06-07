/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.wx.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private OfficeDao officeDao;

    /**
     * 获取所有的店铺
     * 后台使用机构作为店铺，返回给小程序的是机构的code和name。其他表关联的都是机构的code
     */
    public List<Store> listAllStore() {
        //暂时先用这个方法  ？？？
        List<Office>  offices = officeDao.findAllList();
        List<Store> stores = new ArrayList<>();
        for (Office office : offices) {
            Store store = new Store();
            store.Office2Store(office);
            stores.add(store);
        }
        return stores;
    }

    /**
     * 根据id获取店铺
     * 后台使用机构作为店铺，返回给小程序的是机构的code和name。其他表关联的都是机构的code
     */
    public Store findStoreById(String storeId) {
        Office office = new Office();
        office.setCode(storeId);
        office = officeDao.get(office);
        Store store = new Store();
        store.Office2Store(office);
        return store;
    }

}