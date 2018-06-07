package com.thinkgem.jeesite.modules.wx.entity;

import com.thinkgem.jeesite.modules.sys.entity.Office;

import java.io.Serializable;

/**
 *  店铺
 */
public class Store implements Serializable {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void Office2Store(Office office) {
        this.id = office.getCode();
        this.name = office.getName();
    }
}
