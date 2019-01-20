package com.youzi.framework.base.mvp;

import android.content.Context;

import com.youzi.framework.common.compat.rx2.ILifeCycleProvider;
import com.youzi.framework.common.ui.ILoadingHelper;
import com.youzi.framework.common.ui.IToast;
import com.youzi.framework.common.ui.refresh.IRefreshLayout;

import io.reactivex.ObservableTransformer;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public interface IBaseView extends ILifeCycleProvider {
    Context provideContext();

    IToast provideToast();

    void finishPage();

    ILoadingHelper provideLoadingHelper();

    IRefreshLayout provideRefreshLayout();

    <S> ObservableTransformer<S, S> newDialogLoadingTransformer(String... params);

    <S> ObservableTransformer<S, S> newContentLoadingTransformer(ILoadingHelper.OnRetryListener onRetryListener);
}
