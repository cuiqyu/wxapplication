package com.thinkgem.jeesite.modules.wx.entity;

import com.thinkgem.jeesite.modules.wx.constant.WechatConstant;

public class PostWxAuth {

    public String appid;
    public String secret;
    public String js_code;
    public String grant_type;

    public PostWxAuth(String js_code) {
        this.appid = WechatConstant.appid;
        this.secret = WechatConstant.key;
        this.js_code = js_code;
        this.grant_type = "authorization_code";
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getJs_code() {
        return js_code;
    }

    public void setJs_code(String js_code) {
        this.js_code = js_code;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
