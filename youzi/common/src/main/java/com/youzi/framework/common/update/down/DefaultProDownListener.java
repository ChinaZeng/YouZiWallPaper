package com.youzi.framework.common.update.down;

public class DefaultProDownListener extends AbsProxyDownListener {
    public DefaultProDownListener(DownListener listener) {
        super(listener);
    }

    @Override
    public void onFail(DownTask task, int code, String msg) {
        if (listener != null) {
            listener.onFail(task, code, msg);
        }
    }

    @Override
    public void onProgress(DownTask task, long progress, long totalSize) {
        if (listener != null) {
            listener.onProgress(task, progress, totalSize);
        }
    }

    @Override
    public void onSuccess(DownTask task) {
        if (listener != null) {
            listener.onSuccess(task);
        }
    }
}
