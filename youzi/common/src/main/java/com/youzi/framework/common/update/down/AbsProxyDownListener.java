package com.youzi.framework.common.update.down;

public abstract class AbsProxyDownListener implements DownListener {
    protected DownListener listener;

    public AbsProxyDownListener(DownListener listener) {
        this.listener = listener;
        if (listener == null) {
            this.listener = DownListener.EMPTY;
        }
    }

    public DownListener getListener() {
        return listener;
    }

}
