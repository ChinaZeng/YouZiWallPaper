package com.youzi.framework.common.update.down;


public interface DownListener {
    void onFail(DownTask task, int code, String msg);

    void onProgress(DownTask task, long progress, long totalSize);

    void onSuccess(DownTask task);

    DownListener EMPTY = new DownListener() {
        @Override
        public void onFail(DownTask task, int code, String msg) {

        }

        @Override
        public void onProgress(DownTask task, long progress, long totalSize) {

        }

        @Override
        public void onSuccess(DownTask task) {

        }
    };
}
