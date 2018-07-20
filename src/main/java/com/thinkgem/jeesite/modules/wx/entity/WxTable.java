/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 桌号管理Entity
 *
 * @author cqy
 * @version 2018-07-20
 */
public class WxTable extends DataEntity<WxTable> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String tableId;        // table_id
    private String tableName;        // table_name
    private String storeId;        // store_id
    private String url;        // url
    private Date createAt;        // create_at
    private Date updateAt;        // update_at

    // 附加字段
    private boolean isShopowner; // 是否是店长
    private String storeName; // 店铺名称

    public WxTable() {
        super();
    }

    public WxTable(String id) {
        super(id);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Length(min = 1, max = 32, message = "table_id长度必须介于 1 和 32 之间")
    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    @Length(min = 0, max = 20, message = "table_name长度必须介于 0 和 20 之间")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Length(min = 0, max = 32, message = "store_id长度必须介于 0 和 32 之间")
    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Length(min = 0, max = 50, message = "url长度必须介于 0 和 50 之间")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "create_at不能为空")
    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "update_at不能为空")
    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public boolean getIsShopowner() {
        return isShopowner;
    }

    public void setIsShopowner(boolean shopowner) {
        isShopowner = shopowner;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

}