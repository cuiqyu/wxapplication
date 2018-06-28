package com.thinkgem.jeesite.modules.wx.entity;

import com.thinkgem.jeesite.modules.wx.constant.WechatConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class PostWxOrder {

    //微信分配的小程序ID
    @XStreamAlias("appid")
    public String appid;

    //微信支付分配的商户号
    @XStreamAlias("mch_id")
    public String mch_id;

    //随机字符串，长度要求在32位以内。推荐随机数生成算法
    @XStreamAlias("nonce_str")
    public String nonce_str;

    //通过签名算法计算得出的签名值，详见签名生成算法
    @XStreamAlias("sign")
    public String sign;

    //商品简单描述，该字段请按照规范传递，具体请见参数规定
    @XStreamAlias("body")
    public String body;

    //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一。详见商户订单号
    @XStreamAlias("out_trade_no")
    public String out_trade_no;

    //订单总金额，单位为分，详见支付金额
    @XStreamAlias("total_fee")
    public int total_fee;

    //APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
    @XStreamAlias("spbill_create_ip")
    public String spbill_create_ip;

    //异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
    @XStreamAlias("notify_url")
    public String notify_url;

    //小程序取值如下：JSAPI，详细说明见参数规定
    @XStreamAlias("trade_type")
    public String trade_type;

    //trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】
    @XStreamAlias("openid")
    public String openid;

    public PostWxOrder(String sign, String out_trade_no, int total_fee, String openid) {
        this.appid = WechatConstant.appid;
        this.mch_id = WechatConstant.mch_id;
        this.nonce_str = WechatConstant.nonce_str;
        this.sign = sign;
        this.body = WechatConstant.body;
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.spbill_create_ip = WechatConstant.spbill_create_ip;
        this.notify_url = WechatConstant.notify_url;
        this.trade_type = WechatConstant.trade_type;
        this.openid = openid;
    }

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
