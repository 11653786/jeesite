package com.thinkgem.jeesite.api.entity.res;

import com.thinkgem.jeesite.api.enums.ResCodeMsgType;

/**
 * Created by yangtao on 2017/8/18.
 */
public class PlatformRes<T> {

    private String code;
    private String message;
    private T data;

    public PlatformRes() {
    }

    public PlatformRes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public PlatformRes(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> PlatformRes<T> success(T t) {
        return new PlatformRes<T>(ResCodeMsgType.SUCCESS_.code(), ResCodeMsgType.SUCCESS_.desc(), t);
    }

    public static <T> PlatformRes<T> fail() {
        return new PlatformRes<T>(ResCodeMsgType.FAIL.code(), ResCodeMsgType.FAIL.desc());
    }

    public static <T> PlatformRes<T> error(ResCodeMsgType resCodeMsgType) {
        return new PlatformRes<T>(resCodeMsgType.code(), resCodeMsgType.desc());
    }

    public static <T> PlatformRes<T> error(String code, String msg) {
        return new PlatformRes<T>(code, msg);
    }

}
