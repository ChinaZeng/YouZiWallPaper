package com.youzi.framework.common.ui.dialog.abs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.youzi.framework.common.ui.dialog.i.ILoadingDialog;

/**
 * 功能描述:加载进度等待对话框
 * Created by LuoHaifeng on 2017/6/28.
 * Email:496349136@qq.com
 */

public abstract class LoadingDialog extends BaseDialog implements ILoadingDialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public abstract void setMessage(CharSequence message);

    @Override
    public void setMessage(@StringRes int message) {
        setMessage(getContext().getString(message));
    }
}
