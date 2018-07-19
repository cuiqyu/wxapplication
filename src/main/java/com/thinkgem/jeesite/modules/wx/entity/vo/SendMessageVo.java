package com.thinkgem.jeesite.modules.wx.entity.vo;

import java.io.Serializable;

/**
 * 发送消息的对象
 *
 * @auther cuiqiongyu
 * @create 2018/7/17 18:00
 */
public class SendMessageVo implements Serializable {

    private static final long serialVersionUID = 5823475070190306808L;

    private String touser; // 接受者的openId
    private String template_id; // 消息模板id
    private String page; // 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
    private String form_id; // 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
    private String data; // 模板数据
    private String emphasis_keyword; // 需要放大的关键字

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmphasis_keyword() {
        return emphasis_keyword;
    }

    public void setEmphasis_keyword(String emphasis_keyword) {
        this.emphasis_keyword = emphasis_keyword;
    }

}
