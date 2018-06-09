package com.thinkgem.jeesite.modules.wx.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 店铺
 */
public class Store extends DataEntity<Store> {

    private static final long serialVersionUID = 1L;

    private String name;        // name
    private String userId;        // user_id

    // 附加字段
    private String userName;

    public Store() {
        super();
    }

    public Store(String id) {
        super(id);
    }

    @Length(min = 1, max = 64, message = "name长度必须介于 1 和 64 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 1, max = 64, message = "user_id长度必须介于 1 和 64 之间")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}