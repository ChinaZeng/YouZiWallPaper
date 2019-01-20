package com.youzi.framework.common.ui;

import android.content.Context;
import android.view.View;

import com.youzi.framework.common.ui.dialog.i.IAlertDialog;
import com.youzi.framework.common.ui.dialog.i.ILoadingDialog;
import com.youzi.framework.common.ui.dialog.i.IProgressDialog;
import com.youzi.framework.common.ui.refresh.IRefreshLayout;
import com.youzi.framework.common.ui.toolbar.IToolbar;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public interface IUIProvider {
    ILoadingHelper provideLoadingHelper(View view);

    IRefreshLayout newRefreshLayout(Context context);

    IToast provideToast();

    IAlertDialog newAlertDialog(Context context);

    ILoadingDialog newLoadingDialog(Context context);

    IProgressDialog newProgressDialog(Context context);

    IToolbar newToolbar(Context context);
}
