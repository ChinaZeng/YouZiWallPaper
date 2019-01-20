package com.youzi.framework.common.update.down;

public class DefaultProxyTaskListener extends AbsProxyTaskListener {

    public DefaultProxyTaskListener(TaskListener listener) {
        super(listener);
    }

    @Override
    public void onTaskCreate(DownTask task) {
        if (listener != null) {
            listener.onTaskCreate(task);
        }
    }

    @Override
    public void onTaskStart(DownTask task) {

        if (listener != null) {
            listener.onTaskStart(task);
        }
    }

    @Override
    public void onTaskEnd(DownTask task) {
        if (listener != null) {
            listener.onTaskEnd(task);
        }
    }
}
