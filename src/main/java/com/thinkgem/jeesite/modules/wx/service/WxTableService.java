/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.thinkgem.jeesite.common.entity.ActionBaseDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wx.entity.WxTable;
import com.thinkgem.jeesite.modules.wx.dao.WxTableDao;

/**
 * 桌号管理Service
 *
 * @author cqy
 * @version 2018-07-20
 */
@Service
@Transactional(readOnly = true)
public class WxTableService extends CrudService<WxTableDao, WxTable> {

    private final static Logger logger = LoggerFactory.getLogger(WxTableService.class);

    @Autowired
    private WxService wxService;

    @Autowired
    private WxTableDao wxTableDao;

    public WxTable get(String id) {
        return super.get(id);
    }

    public List<WxTable> findList(WxTable wxTable) {
        return super.findList(wxTable);
    }

    public Page<WxTable> findPage(Page<WxTable> page, WxTable wxTable) {
        return super.findPage(page, wxTable);
    }

    @Transactional(readOnly = false)
    public void save(WxTable wxTable) {
        super.save(wxTable);
    }

    @Transactional(readOnly = false)
    public void delete(WxTable wxTable) {
        super.delete(wxTable);
    }

    @Transactional(readOnly = false)
    public ActionBaseDto insert(WxTable wxTable, String fileUploadPath, String savePath) {
        if (null == wxTable) {
            logger.error("新增桌号失败，参数不能为空！");
            return ActionBaseDto.getFailedInstance("参数不能为空");
        }
        if (StringUtils.isEmpty(wxTable.getTableId())) {
            logger.error("新增桌号失败，桌号不能为空！");
            return ActionBaseDto.getFailedInstance("桌号不能为空");
        }
        if (StringUtils.isEmpty(wxTable.getStoreId())) {
            logger.error("新增桌号失败，店铺不能为空！");
            return ActionBaseDto.getFailedInstance("店铺不能为空");
        }

        // 相同的店铺不能添加相同的桌号
        WxTable wxTable1 = new WxTable();
        wxTable1.setStoreId(wxTable.getStoreId());
        wxTable1.setTableId(wxTable.getTableId());
        List<WxTable> list = findList(wxTable1);
        if (CollectionUtils.isNotEmpty(list)) {
            logger.error("新增桌号失败，相同的店铺不能添加重复的桌号！");
            return ActionBaseDto.getFailedInstance("相同的店铺不能添加重复的桌号");
        }

        // 调用微信生成小程序码的接口
        String urlParam = new StringBuffer("?storeId=")
                .append(wxTable.getStoreId()).append("&tableNum=").append(wxTable.getTableId()).toString();
        String fileName = new Date().getTime() + ".png";
        ActionBaseDto<String> generatorQrCode = wxService.generatorQrCode(urlParam, fileUploadPath + "/" + fileName, fileUploadPath, savePath + "/" + fileName);
        if (generatorQrCode.isFailed()) {
            logger.error("新增桌号失败，生成桌号小程序码失败，失败原因：{}", generatorQrCode.getDesc());
            return ActionBaseDto.getFailedInstance("生成桌号小程序码失败，失败原因：" + generatorQrCode.getDesc());
        }

        wxTable.setUrl(generatorQrCode.getData());
        if (StringUtils.isEmpty(wxTable.getId())) {
            wxTable.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        }

        wxTableDao.insert(wxTable);
        return ActionBaseDto.getSuccessInstance();
    }

}