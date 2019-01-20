package com.youzi.framework.common.ui.loading;

import android.view.View;

import com.youzi.framework.common.ui.ILoadingHelper;

public interface ILoadingViewInterceptor {
    View onInflateLoadingView(View originalView, ILoadingHelper.State state);
}
