package com.thinkgem.jeesite.modules.wx.service;

import org.slf4j.Logger;
import com.thinkgem.jeesite.common.entity.ActionBaseDto;
import com.thinkgem.jeesite.modules.wx.entity.vo.AccessToken;
import com.thinkgem.jeesite.modules.wx.utils.HttpUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Date;
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
     * 获取小程序的微信token TODO
     * @return
     */
    public AccessToken getAccessToken() {
        String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + appSecret;
        String accessTokenResult = HttpUtils.post(getAccessTokenUrl, null);
        AccessToken accessToken = HttpUtils.xmlToBean(AccessToken.class, accessTokenResult);
        return accessToken;
    }

    /**
     * 生成桌号的小程序码，并上传至服务器 TODO
     * @param param
     * @return
     */
    public ActionBaseDto<String> generatorQrCode(String param, String fileName, String filePath) {
        // 获取小程序的access_token
        AccessToken accessToken = getAccessToken();
        if (null == accessToken) {
            logger.error("生成小程序码失败，获取小程序的access_token失败！");
            return ActionBaseDto.getFailedInstance("获取小程序的access_token失败");
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            String getQrCodeUrl = "https://api.weixin.qq.com/wxa/getwxacode?access_token=11_0qx8dP3nhlfzaNiWiFHz51PUmbkmoMoXfjCxh8yfmfFztM1Eb9--US3-Jnl1Z4EmVUe9Dmm4G4sqrJduZxWHpybPs2hiJaNRB71eUP8qyAHcobdTtLv7CG7xTaADdheMLQqxtcvJbrqvOT53YQCdAGARJV";

            RestTemplate rest = new RestTemplate();
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("page", table_number_jump_link + param);
            paramMap.put("width", qr_code_width);
            paramMap.put("auto_color", false);
            Map<String,Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            paramMap.put("line_color", line_color);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(paramMap, headers);
            ResponseEntity<byte[]> entity = rest.exchange(getQrCodeUrl, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);

            File f=new File(filePath);
            if(!f.exists()){
                f.mkdirs();
            }

            File file = new File(fileName);
            if (!file.exists()){
                file.createNewFile();
            }

            bis = new BufferedInputStream(inputStream);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] by=new byte[1024];
            int length=0;
            while((length=bis.read(by))!=-1){
                bos.write(by,0,length);
            }
            return ActionBaseDto.getSuccessInstance("", file.getAbsolutePath());
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
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
