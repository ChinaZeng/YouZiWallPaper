package com.youzi.framework.common.ui.refresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.youzi.framework.common.Config;

public class ProxyRefreshLayout extends AbstractRefreshLayout {
    public ProxyRefreshLayout(Context context) {
        super(context);
    }

    public ProxyRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProxyRefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected IRefreshLayout provideRealRefreshLayout() {
        return Config.getUiProvider().newRefreshLayout(getContext());
    }
}