package com.thinkgem.jeesite.common.entity;

/**
 * @auther cuiqiongyu
 * @create 2018/6/4 23:14
 */
public enum ActionBaseCode {

    NORMAL_SUCCESS("000000", "成功")
    ;

    private String code;
    private String desc;

    ActionBaseCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toString() {
        return this.code + "||" + this.desc;
    }

}
