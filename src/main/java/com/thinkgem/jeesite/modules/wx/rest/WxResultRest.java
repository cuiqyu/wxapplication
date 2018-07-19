package com.thinkgem.jeesite.modules.wx.rest;

import com.thinkgem.jeesite.modules.wx.entity.PostWxAuth;
import com.thinkgem.jeesite.modules.wx.entity.vo.QueryOrderStateVo;
import com.thinkgem.jeesite.modules.wx.entity.vo.WxAuthVo;
import com.thinkgem.jeesite.modules.wx.entity.vo.WxResultResponceVo;
import com.thinkgem.jeesite.modules.wx.entity.vo.WxResultVo;
import com.thinkgem.jeesite.modules.wx.service.OrderService;
import com.thinkgem.jeesite.modules.wx.utils.HttpUtils;
import com.thinkgem.jeesite.modules.wx.utils.JsonUtils;
import com.thinkgem.jeesite.modules.wx.utils.MD5Util;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.thinkgem.jeesite.modules.wx.constant.WechatConstant.*;
import static com.thinkgem.jeesite.modules.wx.constant.WechatConstant.key;

@RestController
@RequestMapping("/api/wx/result")
public class WxResultRest {

    private final static Logger logger = LoggerFactory.getLogger(WxResultRest.class);

    @Autowired
    private OrderService orderService;

    /**
     * wx支付成功回调
     */
    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
    public String wxResult(HttpServletRequest request) {
        try {
            String xml = IOUtils.toString(request.getInputStream());
            WxResultVo wxResultVo = HttpUtils.xmlToBean(WxResultVo.class, xml);
            orderService.updateOrderState(wxResultVo.getOut_trade_no());
            WxResultResponceVo wxResultResponceVo = new WxResultResponceVo();
            wxResultResponceVo.setReturn_code("SUCCESS");
            wxResultResponceVo.setReturn_msg("OK");
            logger.info("recive wx callback");
            // // 消息推送
            // pushMessage(wxResultVo);

            return HttpUtils.beanToXml(wxResultResponceVo);
        } catch (IOException e) {
            WxResultResponceVo wxResultResponceVo = new WxResultResponceVo();
            wxResultResponceVo.setReturn_code("FAIL");
            wxResultResponceVo.setReturn_msg("参数格式校验错误");
            return HttpUtils.beanToXml(wxResultResponceVo);
        }
    }

    /**
     * 小程序查询订单状态
     */
    @RequestMapping(value = "/auth", method = {RequestMethod.GET})
    public String wxAuth(@RequestParam("code") String code) {

        //----------------------------调用微信登录凭证校验---------------------------
        //https://developers.weixin.qq.com/miniprogram/dev/api/api-login.html#wxloginobject
        PostWxAuth postWxAuth = new PostWxAuth(code);
        String string = "https://api.weixin.qq.com/sns/jscode2session";
        String url = string + "?" + "appid=" + postWxAuth.getAppid()
            + "&" + "secret=" + postWxAuth.getSecret() + "&" + "js_code=" + postWxAuth.getJs_code()
            + "&" + "grant_type=" + postWxAuth.getGrant_type();
        String aa = HttpUtils.post(url, postWxAuth);
        return aa;

    }


    /**
     * 小程序查询订单状态
     */
    @RequestMapping(value = "order/state", method = {RequestMethod.GET})
    public boolean queryOrderState(@RequestParam("out_trade_no") String out_trade_no) {

        //签名
        String stringA =
            "appid=" + appid +
                "&" + "mch_id=" + mch_id +
                "&" + "nonce_str=" + nonce_str +
                "&" + "out_trade_no=" + out_trade_no;

        String SignTemp = stringA + "&key=" + key;
        String sign = MD5Util.md5(SignTemp).toUpperCase();

        QueryOrderStateVo queryOrderStateVo = new QueryOrderStateVo(out_trade_no, sign);
        String x = HttpUtils.post("https://api.mch.weixin.qq.com/pay/orderquery", queryOrderStateVo);
//        OrderVo vo = HttpUtils.beanToXml(OrderVo.class, x);
//        if ("SUCCESS".equals(vo.getReturn_code()) && "SUCCESS".equals(vo.getResult_code())) {
//            orderDao.addOrder(order);
//        }
        return true;
    }

    /**
     * 消息推送
     * @param wxResultVo
     */
    private void pushMessage(WxResultVo wxResultVo) {
        logger.info("push message to user start--param:{}", JsonUtils.Object2String(wxResultVo));
        if (null == wxResultVo) {
            logger.info("push message to user end--result:{}", "推送失败，参数为空");
            return;
        }

        // 获取我们的accessToken
        String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        String accessTokenResult = HttpUtils.post(getAccessTokenUrl, null);
        AccessToken accessToken = HttpUtils.xmlToBean(AccessToken.class, accessTokenResult);
        if (null == accessToken) {
            logger.info("push message to user end--result:{}", "推送失败，获取小程序的accessToken失败");
            return;
        }

        // 给用户推送消息
        String pushUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken.getAccess_token();
        SendMessageVo message = new SendMessageVo();
        message.setTouser(wxResultVo.getOpenid());
        message.setTemplate_id(order_pay_success_template_id); // 支付成功 TODO
        message.setEmphasis_keyword("keyword1.DATA"); // 订单号加粗
        message.setForm_id(""); // TODO
        // 构建模板对象
        SendMessageDateVo sendMessageDateVo = new SendMessageDateVo();
        sendMessageDateVo.setKeyword1(new KeyWord("")); // 订单id TODO
        sendMessageDateVo.setKeyword2(new KeyWord("")); // 支付金额 TODO
        // sendMessageDateVo.setKeyword3(new KeyWord("")); // 支付方式 TODO
        sendMessageDateVo.setKeyword4(new KeyWord("")); // 订单时间 TODO
        // sendMessageDateVo.setKeyword5(new KeyWord("")); // 下单门店 TODO
        sendMessageDateVo.setKeyword6(new KeyWord("")); // 桌位号 TODO

        message.setData(JsonUtils.Object2String(sendMessageDateVo)); // 模板内容
        String sendMessageResult = HttpUtils.post(pushUrl, message);
        SendMessageResultVo sendMessageResultVo = HttpUtils.xmlToBean(SendMessageResultVo.class, sendMessageResult);
        if (sendMessageResultVo.getErrcode() == 0) {
            logger.info("push message to user end--result:{}", "success");
        } else {
            logger.info("push message to user end--result:error:{}", sendMessageResultVo.getErrmsg());
        }
    }

}
