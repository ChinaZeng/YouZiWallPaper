package com.youzi.framework.common.ui.toolbar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.youzi.framework.common.Config;

/**
 * Created by Administrator on 2017\11\15 0015.
 */

public class ProxyToolbar extends AbstractToolbar {
    public ProxyToolbar(Context context) {
        super(context);
    }

    public ProxyToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProxyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected IToolbar provideRealToolbar() {
        return Config.getUiProvider().newToolbar(getContext());
    }
}
