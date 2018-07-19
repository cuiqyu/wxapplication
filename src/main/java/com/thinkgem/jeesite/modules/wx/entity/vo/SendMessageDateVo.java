package com.thinkgem.jeesite.modules.wx.entity.vo;

import java.io.Serializable;

/**
 * 推送消息模板对象
 *
 * @auther cuiqiongyu
 * @create 2018/7/17 21:32
 */
public class SendMessageDateVo implements Serializable {

    private static final long serialVersionUID = -8528511081583340924L;

    private KeyWord keyword1; // 订单标号
    private KeyWord keyword2; // 支付金额
    private KeyWord keyword3; // 支付方式
    private KeyWord keyword4; // 订单时间
    private KeyWord keyword5; // 下单门店
    private KeyWord keyword6; // 桌位号

    public KeyWord getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(KeyWord keyword1) {
        this.keyword1 = keyword1;
    }

    public KeyWord getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(KeyWord keyword2) {
        this.keyword2 = keyword2;
    }

    public KeyWord getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(KeyWord keyword3) {
        this.keyword3 = keyword3;
    }

    public KeyWord getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(KeyWord keyword4) {
        this.keyword4 = keyword4;
    }

    public KeyWord getKeyword5() {
        return keyword5;
    }

    public void setKeyword5(KeyWord keyword5) {
        this.keyword5 = keyword5;
    }

    public KeyWord getKeyword6() {
        return keyword6;
    }

    public void setKeyword6(KeyWord keyword6) {
        this.keyword6 = keyword6;
    }

}
