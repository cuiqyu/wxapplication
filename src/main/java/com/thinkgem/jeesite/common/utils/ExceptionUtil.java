package com.thinkgem.jeesite.common.utils;

/**
 * 异常信息工具类
 *
 * @auther cuiqiongyu
 * @create 2018/6/5 22:26
 */
public class ExceptionUtil {

    public static String getDesc(Throwable throwable) {
        String message = throwable.getMessage();
        if (message != null && message.contains("Exception:")) {
            message = message.substring(message.lastIndexOf("Exception:") + 10);
        }
        return message;
    }

}
