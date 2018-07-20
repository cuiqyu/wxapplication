package com.thinkgem.jeesite.modules.wx.entity.vo;

import java.io.Serializable;

/**
 * 获取二维码的请求参数
 *
 * @auther cuiqiongyu
 * @create 2018/7/20 22:07
 */
public class QrCodeVO implements Serializable {

    private static final long serialVersionUID = -562774631001928081L;
    private String path;
    private int width;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
