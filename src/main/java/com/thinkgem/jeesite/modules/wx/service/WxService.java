package com.thinkgem.jeesite.modules.wx.service;

import com.sun.corba.se.spi.orbutil.fsm.ActionBase;
import com.thinkgem.jeesite.modules.wx.entity.vo.QrCodeVO;
import org.slf4j.Logger;
import com.thinkgem.jeesite.common.entity.ActionBaseDto;
import com.thinkgem.jeesite.modules.wx.entity.vo.AccessToken;
import com.thinkgem.jeesite.modules.wx.utils.HttpUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import static com.thinkgem.jeesite.modules.wx.constant.WechatConstant.*;

/**
 * 微信服务端口
 *
 * @auther cuiqiongyu
 * @create 2018/7/20 21:48
 */
@Service
public class WxService {

    private final static Logger logger = LoggerFactory.getLogger(WxService.class);

    /**
     * 获取小程序的微信token
     * @return
     */
    public AccessToken getAccessToken() {
        String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + appSecret;
        String accessTokenResult = HttpUtils.post(getAccessTokenUrl, null);
        AccessToken accessToken = HttpUtils.xmlToBean(AccessToken.class, accessTokenResult);
        return accessToken;
    }

    /**
     * 生成桌号的小程序码，并上传至服务器
     * @param param
     * @return
     */
    public ActionBaseDto<String> generatorQrCode(String param) {
        // 获取小程序的access_token
        AccessToken accessToken = getAccessToken();
        if (null == accessToken) {
            logger.error("生成小程序码失败，获取小程序的access_token失败！");
            return ActionBaseDto.getFailedInstance("获取小程序的access_token失败");

        }
        String getQrCodeUrl = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" +
                accessToken.getAccess_token();

        QrCodeVO codeVO = new QrCodeVO();
        codeVO.setPath(table_number_jump_link + param);
        codeVO.setWidth(qr_code_width);

        String result = HttpUtils.post(getQrCodeUrl, codeVO);
        System.out.println(result);

        // TODO 得到文件，保存到服务器，返回url


        return ActionBaseDto.getSuccessInstance();
    }

}
