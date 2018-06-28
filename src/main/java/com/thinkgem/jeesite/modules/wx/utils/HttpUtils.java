package com.thinkgem.jeesite.modules.wx.utils;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpUtils {


    public static <T> String beanToXml(T t) {
        XStream xStream = new XStream(
            new DomDriver(null,new XmlFriendlyNameCoder("_-", "_")));
        xStream.ignoreUnknownElements();
        xStream.processAnnotations(t.getClass());

        return xStream.toXML(t);
    }

    public static <T> T xmlToBean(Class<T> t, String xml) {

        XStream xStream = new XStream( new DomDriver(null,new XmlFriendlyNameCoder("_-", "_")));
        xStream.ignoreUnknownElements();
        xStream.processAnnotations(t);
        xStream.alias("xml", t);
        return (T) xStream.fromXML(xml);
    }

    public static void validateSign(String newSign, String oldSign) {
        if (!newSign.trim().equals(oldSign.trim())) {
            throw new IllegalArgumentException("签名被修改");
        }

    }


    public static <T> String post(String url, T t) {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        String xml = beanToXml(t);
        System.out.println(xml);
        StringEntity payload = new StringEntity(xml, "UTF-8");
        post.setEntity(payload);
        String text;
        try {
            text = client.execute(post, response -> {
                StringBuilder builder = new StringBuilder();
                HttpEntity entity = response.getEntity();
                String text1;
                if (entity != null) {
                    BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                    while ((text1 = bufferedReader.readLine()) != null) {
                        builder.append(text1);
                    }

                }
                return builder.toString();
            });
        } catch (Exception e) {
            throw new IllegalArgumentException("发送失败!");
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                throw new IllegalArgumentException("HTTP CLIENT 关闭失败");
            }
        }
        return text;
    }


}
