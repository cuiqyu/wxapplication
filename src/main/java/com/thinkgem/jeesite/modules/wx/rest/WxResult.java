package com.thinkgem.jeesite.modules.wx.rest;

import com.thinkgem.jeesite.modules.wx.entity.vo.WxResultResponceVo;
import com.thinkgem.jeesite.modules.wx.entity.vo.WxResultVo;
import com.thinkgem.jeesite.modules.wx.service.OrderService;
import com.thinkgem.jeesite.modules.wx.utils.HttpUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/wx/result")
public class WxResult {

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
            return wxResultResponceVo;
        } catch (IOException e) {
            throw new IllegalAccessException("回调不成功");
        }
    }
}
