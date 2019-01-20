package com.youzi.framework.common.error;

/**
 * Created by LuoHaifeng on 2018/4/12 0012.
 * Email:496349136@qq.com
 */
public class ProcessResultException extends ProxyException {

    public ProcessResultException(Exception realException) {
        super(realException);
    }
}
