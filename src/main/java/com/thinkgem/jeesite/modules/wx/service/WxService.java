package com.thinkgem.jeesite.modules.wx.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.modules.wx.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import com.thinkgem.jeesite.common.entity.ActionBaseDto;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
    public String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        url = url.replace("APPID", appid).replace("APPSECRET", appSecret);

        String result = HttpUtil.doGetSSL(url, "utf-8");
        JSONObject json = JSONObject.parseObject(result);
        String access_token = (String) json.get("access_token");
        return access_token;
    }

    /**
     * 生成桌号的小程序码，并上传至服务器
     * @param param
     * @return
     */
    public ActionBaseDto<String> generatorQrCode(String param, String fileName, String filePath, String savePath) {
        // 获取小程序的access_token
        String accessToken = getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            logger.error("生成小程序码失败，获取小程序的access_token失败！");
            return ActionBaseDto.getFailedInstance("获取小程序的access_token失败");
        }

        InputStream inputStream = null;
        FileOutputStream out = null;
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("path", table_number_jump_link + param);
            paramMap.put("width", qr_code_width);
            paramMap.put("auto_color", false);
            Map<String,Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            paramMap.put("line_color", line_color);

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacode?access_token="+accessToken);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            String body = JSON.toJSONString(paramMap);
            StringEntity entitys = new StringEntity(body,"utf-8");
            entitys.setContentType("text/json");
            entitys.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            httpPost.setEntity(entitys);
            HttpResponse response = httpClient.execute(httpPost);
            inputStream = response.getEntity().getContent();

            File targetFile = new File(filePath);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            out = new FileOutputStream(fileName);

            byte[] buffer = new byte[8192];
            int bytesRead = 0;
            while((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            out.flush();
            out.close();

            return ActionBaseDto.getSuccessInstance("", savePath);
        }  catch (Exception e) {
            e.printStackTrace();
            return ActionBaseDto.getFailedInstance("调用生成小程序码接口失败！");
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
