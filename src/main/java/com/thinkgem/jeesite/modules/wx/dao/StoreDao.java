package com.thinkgem.jeesite.modules.wx.dao;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.entity.Store;

import java.util.List;


/**
 * 店铺DAO接口
 * @author tgp
 * @version 2018-06-04
 */
@MyBatisDao
public interface StoreDao {

    Store findStoreById(String storeId);

    List<Store> listAllStore();

}