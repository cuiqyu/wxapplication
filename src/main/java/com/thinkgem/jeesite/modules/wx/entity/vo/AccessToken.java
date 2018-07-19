package com.thinkgem.jeesite.modules.wx.entity.vo;

import java.io.Serializable;

/**
 * accessToken对象
 *
 * @auther cuiqiongyu
 * @create 2018/7/17 17:42
 */
public class AccessToken implements Serializable {

    private String access_token;
    private long expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

}
