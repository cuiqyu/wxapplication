package com.thinkgem.jeesite.modules.wx.entity.vo;

import com.thinkgem.jeesite.modules.wx.constant.WechatConstant;

public class QueryOrderStateVo {

    private String appid;
    private String mch_id;
    private String out_trade_no;
    private String nonce_str;
    private String sign;
    private String sign_type;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public QueryOrderStateVo(String out_trade_no, String sign) {
        this.appid = WechatConstant.appid;
        this.mch_id = WechatConstant.mch_id;
        this.nonce_str = WechatConstant.nonce_str;
        this.sign = sign;
        this.sign_type = WechatConstant.trade_type;
        this.out_trade_no = out_trade_no;
    }
}
