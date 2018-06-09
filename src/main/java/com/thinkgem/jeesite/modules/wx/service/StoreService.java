package com.thinkgem.jeesite.modules.wx.service;

import java.util.List;
import java.util.UUID;

import com.thinkgem.jeesite.modules.wx.entity.FoodCategory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.dao.StoreDao;

/**
 * 店铺Service
 *
 * @author tgp
 * @version 2018-06-04
 */
@Service
@Transactional(readOnly = true)
public class StoreService extends CrudService<StoreDao, Store> {

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

    public Store get(String id) {
        return super.get(id);
    }

    public List<Store> findList(Store store) {
        return super.findList(store);
    }

    public Page<Store> findPage(Page<Store> page, Store store) {
        return super.findPage(page, store);
    }

    @Transactional(readOnly = false)
    public void save(Store store) {
        if (StringUtils.isEmpty(store.getId())) {
            store.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            storeDao.insert(store);
        } else {
            storeDao.update(store);
        }
    }

    @Transactional(readOnly = false)
    public void delete(Store store) {
        super.delete(store);
    }

    public Store getByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        Store store = storeDao.getByName(name);
        return store;
    }

}