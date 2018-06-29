package com.thinkgem.jeesite.modules.wx.rest;

import com.thinkgem.jeesite.modules.wx.entity.PostWxOrder;
import com.thinkgem.jeesite.modules.wx.entity.vo.OrderVo;
import com.thinkgem.jeesite.modules.wx.entity.vo.QueryOrderStateVo;
import com.thinkgem.jeesite.modules.wx.entity.vo.WxResultResponceVo;
import com.thinkgem.jeesite.modules.wx.entity.vo.WxResultVo;
import com.thinkgem.jeesite.modules.wx.service.OrderService;
import com.thinkgem.jeesite.modules.wx.utils.HttpUtils;
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
    public WxResultResponceVo wxResult(HttpServletRequest request) throws IllegalAccessException {
        try {
            String xml = IOUtils.toString(request.getInputStream());
            WxResultVo wxResultVo = HttpUtils.xmlToBean(WxResultVo.class, xml);
            orderService.updateOrderState(wxResultVo.getOut_trade_no());
            WxResultResponceVo wxResultResponceVo = new WxResultResponceVo();
            wxResultResponceVo.setReturn_code("SUCCESS");
            wxResultResponceVo.setReturn_msg("OK");
            logger.info("成功收到微信回掉更新订单状态");
            return wxResultResponceVo;
        } catch (IOException e) {
            throw new IllegalAccessException("回调不成功");
        }
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
}
