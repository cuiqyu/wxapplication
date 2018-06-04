package com.thinkgem.jeesite.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


import java.io.Serializable;

/**
 * 相应参数对象
 *
 * @auther cuiqiongyu
 * @create 2018/6/4 21:37
 */
@JsonIgnoreProperties(ignoreUnknown=true) // 忽略掉没有配置项
@JsonInclude(Include.NON_NULL)
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 8843280935490813379L;

    private Long seq;
    private String state;
    private String msg;
    private T data;
    private Integer offset;
    private Long total;

    public ResponseData() {}
    public ResponseData(String[] state, T data) {
        this.state = state[0];
        this.msg = state[1];
        this.data = data;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

}
