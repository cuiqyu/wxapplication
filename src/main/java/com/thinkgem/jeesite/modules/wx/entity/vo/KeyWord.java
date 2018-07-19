package com.thinkgem.jeesite.modules.wx.entity.vo;

import java.io.Serializable;

/**
 * @auther cuiqiongyu
 * @create 2018/7/17 21:47
 */
public class KeyWord implements Serializable {

    private static final long serialVersionUID = 2337139831356933288L;
    private String value;

    public KeyWord(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
