package com.youzi.framework.common.update;

public interface IUpdate {

    void start();

    /**
     * 目前没有实现这个功能
     */
    void pause();

    void progressUpdate(int progress);

    void success(String filePath);

    void fail(int code, String msg);
}
