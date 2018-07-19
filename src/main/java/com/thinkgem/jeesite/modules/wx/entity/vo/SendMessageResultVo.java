package com.thinkgem.jeesite.modules.wx.entity.vo;

import java.io.Serializable;

/**
 * 消息推送的结果
 *
 * @auther cuiqiongyu
 * @create 2018/7/17 18:15
 */
public class SendMessageResultVo implements Serializable {

    private static final long serialVersionUID = 1942182775079923768L;

    private int errcode;
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

}
