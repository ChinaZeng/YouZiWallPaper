package com.youzi.service.api.resp;

import android.text.TextUtils;

import com.youzi.framework.common.error.CodeException;

/**
 * Created by zzw on 2019/1/11
 */

public class Resp<T> {

    public static final int CODE_200 = 200;//成功
    public static final int CODE_0 = 0;//成功
    public static final int CODE_20001 = 20001;//成功
    public static final int CODE_400 = 400;//暂无数据
    public static final int CODE_100 = 100;
    public static final int CODE_500 = 500;

    private int code;
    private String message;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        String messageStr = message;
        if (TextUtils.isEmpty(messageStr)) {
            messageStr = msg;
        }

        return messageStr;
    }

    public T getData() {
        return data;
    }

    public Resp<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public Resp<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Resp<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return code == CODE_200 ;
    }

    public CodeException getException() {
        return new CodeException(String.valueOf(code), getMessage());
    }
}
