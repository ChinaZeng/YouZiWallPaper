package com.youzi.framework.common.update.down;

public abstract class AbsProxyTaskListener implements TaskListener {
    protected TaskListener listener;

    public AbsProxyTaskListener(TaskListener listener) {
        this.listener = listener;
        if (this.listener == null) {
            this.listener = TaskListener.EMPTY;
        }
    }

    public TaskListener getListener() {
        return listener;
    }
}
