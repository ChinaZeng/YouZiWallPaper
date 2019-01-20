package com.youzi.framework.uiimpl01;

import android.content.Context;
import android.view.View;

import com.youzi.framework.common.ui.ILoadingHelper;
import com.youzi.framework.common.ui.refresh.IRefreshLayout;
import com.youzi.framework.common.ui.IToast;
import com.youzi.framework.common.ui.IUIProvider;
import com.youzi.framework.common.ui.dialog.i.IAlertDialog;
import com.youzi.framework.common.ui.dialog.i.ILoadingDialog;
import com.youzi.framework.common.ui.dialog.i.IProgressDialog;
import com.youzi.framework.common.ui.toolbar.IToolbar;
import com.youzi.framework.uiimpl01.dialog.AlertDialogImpl;
import com.youzi.framework.uiimpl01.dialog.AlertDialogV2Impl;
import com.youzi.framework.uiimpl01.dialog.AlertDialogV3Impl;
import com.youzi.framework.uiimpl01.dialog.LoadingDialogImpl;
import com.youzi.framework.uiimpl01.dialog.ProgressDialogImpl;
import com.youzi.framework.uiimpl01.loading.LoadingHelperImpl;
import com.youzi.framework.uiimpl01.refresh.RefreshLayoutImpl;
import com.youzi.framework.uiimpl01.toast.ToastImpl;
import com.youzi.framework.uiimpl01.toolbar.ToolbarImpl;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public class UIProvider implements IUIProvider {
    @Override
    public ILoadingHelper provideLoadingHelper(View view) {
        return LoadingHelperImpl.with(view).setLoadingMessage(null);
    }

    @Override
    public IRefreshLayout newRefreshLayout(Context context) {
        return new RefreshLayoutImpl(context);
    }

    @Override
    public IToast provideToast() {
        return ToastImpl.getInstance();
    }

    @Override
    public IAlertDialog newAlertDialog(Context context) {
        return new AlertDialogV3Impl(context);
    }

    @Override
    public ILoadingDialog newLoadingDialog(Context context) {
        return new LoadingDialogImpl(context);
    }

    @Override
    public IProgressDialog newProgressDialog(Context context) {
        return new ProgressDialogImpl(context);
    }

    @Override
    public IToolbar newToolbar(Context context) {
        return new ToolbarImpl(context);
    }
}
