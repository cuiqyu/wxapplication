package com.thinkgem.jeesite.modules.wx.constant;

import com.thinkgem.jeesite.modules.wx.utils.UUIDUtils;
import org.apache.commons.codec.digest.DigestUtils;

public class WechatConstant {

    //微信分配的小程序ID
    public static final String appid = "wx90e34a4cc94f201e";

    //key   app secret
    public static final String key = "1068f4d305f1f0b133ad11d3da5e825c";

    //微信支付分配的商户号
    public static final String mch_id = "1507475751";

    //随机字符串，长度要求在32位以内。推荐随机数生成算法
    public static final String nonce_str = UUIDUtils.timeBasedStr();

    //通过签名算法计算得出的签名值，详见签名生成算法
    public static final String sign = "";

    //商品简单描述，该字段请按照规范传递，具体请见参数规定
    public static final String body = "test";

    //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一。详见商户订单号
    public static final String out_trade_no = "";

    //订单总金额，单位为分，详见支付金额
    public static final String total_fee = "";

    //APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
    public static final String spbill_create_ip = "118.24.91.136";

    //异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
    public static final String notify_url = "118.24.91.136:8080/jeesite/api/HttpUtils";

    //小程序取值如下：JSAPI，详细说明见参数规定
    public static final String trade_type = "JSAPI";

    //trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】
    public static final String openid = "";


    public String doSign() {
        String stringA = "appid=" + appid + "&" + "mch_id=" + mch_id + "&" + "nonce_str=" + nonce_str + "&"
            + "body=" + body + "&" + "out_trade_no=" + out_trade_no + "&" + "total_fee=" + total_fee + "&"
            + "spbill_create_ip=" + spbill_create_ip + "&" + "notify_url=" + notify_url + "&" + "trade_type="
            + trade_type + "&" + "openid=" + openid;

        String SignTemp = stringA + "&key=" + key;

        return DigestUtils.md5Hex(SignTemp).toUpperCase();
    }
}
