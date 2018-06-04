package com.thinkgem.jeesite.common.entity;

import java.io.Serializable;

/**
 * 返回结果对象
 *
 * @auther cuiqiongyu
 * @create 2018/6/4 23:10
 */
public class ActionBaseDto<T> implements Serializable {

    private static final long serialVersionUID = -5568691370231938179L;
    private boolean success;
    private boolean failed;
    private boolean interfaceSuccess;
    private String code;
    private String desc;
    private T data;
    private ActionBaseCode actionBaseCode;

    public ActionBaseDto() {

    }

    public ActionBaseDto(boolean success, String desc, T data) {
        this.success = success;
        this.failed =  !success;
        this.interfaceSuccess = true;
        this.desc = desc;
        this.data = data;
    }

    public ActionBaseDto(boolean interfaceSuccess, boolean success, String desc, T data) {
        this.success = success;
        this.failed =  !success;
        this.interfaceSuccess = interfaceSuccess;
        this.desc = desc;
        this.data = data;
    }

    public ActionBaseDto(boolean interfaceSuccess, boolean success, ActionBaseCode actionBaseCode, T data) {
        this.success = success;
        this.failed =  !success;
        this.interfaceSuccess = interfaceSuccess;
        this.desc = actionBaseCode.getDesc();
        this.code = actionBaseCode.getCode();
        this.actionBaseCode = actionBaseCode;
        this.data = data;
    }

    public static <T> ActionBaseDto<T> getInstance(ActionBaseDto actionBaseDto) {
        ActionBaseDto<T> actionBaseDtoNew = new ActionBaseDto<T>();
        actionBaseDtoNew.success = actionBaseDto.success;
        actionBaseDtoNew.failed = actionBaseDto.failed;
        actionBaseDtoNew.interfaceSuccess = actionBaseDto.interfaceSuccess;
        actionBaseDtoNew.desc = actionBaseDto.desc;

        return actionBaseDtoNew;
    }

    public static <T> ActionBaseDto<T> getInstance(ActionBaseDto actionBaseDto, T data) {
        ActionBaseDto<T> actionBaseDtoNew = new ActionBaseDto<T>();
        actionBaseDtoNew.success = actionBaseDto.success;
        actionBaseDtoNew.failed = actionBaseDto.failed;
        actionBaseDtoNew.interfaceSuccess = actionBaseDto.interfaceSuccess;
        actionBaseDtoNew.desc = actionBaseDto.desc;

        actionBaseDtoNew.setData(data);
        return actionBaseDtoNew;
    }

    /**
     *  接口和结果都成功
     */
    public static <T> ActionBaseDto<T> getSuccessInstance() {
        return new ActionBaseDto<>(true, true, "", null);
    }

    public static <T> ActionBaseDto<T> getSuccessInstance(String desc) {
        return new ActionBaseDto<>(true, true, desc, null);
    }

    public static <T> ActionBaseDto<T> getSuccessInstance(T t) {
        return new ActionBaseDto<>(true, true, "", t);
    }

    public static <T> ActionBaseDto<T> getSuccessInstance(String desc, T t) {
        return new ActionBaseDto<>(true, true, desc, t);
    }

    /**
     *  接口失败
     */
    public static <T> ActionBaseDto<T> getInterfaceFailedInstance(String desc) {
        return new ActionBaseDto<>(false, false, desc, null);
    }

    public static <T> ActionBaseDto<T> getInterfaceFailedInstance() {
        return new ActionBaseDto<>(false, false, "", null);
    }

    public static <T> ActionBaseDto<T> getInterfaceFailedInstance(String desc, T t) {
        return new ActionBaseDto<>(false, false, desc, t);
    }

    public static <T> ActionBaseDto<T> getFailedInstance() {
        return new ActionBaseDto<>(true, false, "", null);
    }

    /**
     *  接口成功，结果失败
     */
    public static <T> ActionBaseDto<T> getFailedInstance(String desc) {
        return new ActionBaseDto<>(true, false, desc, null);
    }

    public static <T> ActionBaseDto<T> getFailedInstance(T t) {
        return new ActionBaseDto<>(true, false, "", t);
    }

    public static <T> ActionBaseDto<T> getFailedInstance(ActionBaseCode actionBaseCode) {
        return new ActionBaseDto<>(true, false, actionBaseCode, null);
    }

    public static <T> ActionBaseDto<T> getFailedInstance(String desc, T t) {
        return new ActionBaseDto<>(true, false, desc, t);
    }

    public static <T> ActionBaseDto<T> getFailedInstance(ActionBaseCode actionBaseCode, T t) {
        return new ActionBaseDto<>(true, false, actionBaseCode, t);
    }

    /**
     *  接口成功，结果失败
     */
    public static <T> ActionBaseDto<T> getResultFailedInstance(String desc) {
        return new ActionBaseDto<>(true, false, desc, null);
    }

    public static <T> ActionBaseDto<T> getResultFailedInstance() {
        return new ActionBaseDto<>(true, false, "", null);
    }

    public static <T> ActionBaseDto<T> getResultFailedInstance(String desc, T t) {
        return new ActionBaseDto<>(true, false, desc, t);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isInterfaceSuccess() {
        return interfaceSuccess;
    }

    public void setInterfaceSuccess(boolean interfaceSuccess) {
        this.interfaceSuccess = interfaceSuccess;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
        this.setFailed(!success);
    }

    public ActionBaseCode getActionBaseCode() {
        return actionBaseCode;
    }

    public void setActionBaseCode(ActionBaseCode actionBaseCode) {
        this.actionBaseCode = actionBaseCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isFailed() {
        return failed;
    }

    private void setFailed(boolean failed) {
        this.failed = failed;
    }

}
