/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.entity.WxTable;

/**
 * 桌号管理DAO接口
 * @author cqy
 * @version 2018-07-20
 */
@MyBatisDao
public interface WxTableDao extends CrudDao<WxTable> {
	
}