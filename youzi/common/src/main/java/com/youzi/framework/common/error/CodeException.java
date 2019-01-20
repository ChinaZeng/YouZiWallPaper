package com.youzi.framework.common.error;

/**
 * Created by LuoHaifeng on 2017/12/26 0026.
 * Email:496349136@qq.com
 */

public class CodeException extends RuntimeException {
    private String code;
    private String message;

    public CodeException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName() + ":" + code + "," + message;
    }
}
