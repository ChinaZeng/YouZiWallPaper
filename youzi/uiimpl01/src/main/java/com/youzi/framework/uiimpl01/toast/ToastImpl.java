package com.youzi.framework.uiimpl01.toast;

import android.content.Context;
import android.support.annotation.StringRes;

import com.youzi.framework.common.Config;
import com.youzi.framework.common.ui.IToast;
import com.youzi.framework.uiimpl01.R;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public class ToastImpl implements IToast {
    private static ToastImpl instance;
    private Context appContext;

    public ToastImpl(Context appContext) {
        this.appContext = appContext;
    }

    public static synchronized ToastImpl getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new ToastImpl(applicationContext);
        }
        return instance;
    }

    public static ToastImpl getInstance() {
        return getInstance(Config.getAppContext());
    }

    @Override
    public void showInfo(CharSequence message) {
        ToastUtil.showToast(message, R.mipmap.uiimpl_ic_info);
    }

    @Override
    public void showInfo(int message) {
        showInfo(getString(message));
    }

    @Override
    public void showWarn(CharSequence message) {
        ToastUtil.showToast(message, R.mipmap.uiimpl_ic_warn);
    }

    @Override
    public void showWarn(int message) {
        showWarn(getString(message));
    }

    @Override
    public void showError(CharSequence message) {
        ToastUtil.showToast(message, R.mipmap.uiimpl_ic_error);
    }

    @Override
    public void showError(int message) {
        showError(getString(message));
    }

    @Override
    public void showSuccess(CharSequence message) {
        ToastUtil.showToast(message, R.mipmap.uiimpl_ic_sucess);
    }

    @Override
    public void showSuccess(int message) {
        showSuccess(getString(message));
    }

    private String getString(@StringRes int stringResId) {
        return Config.getAppContext().getString(stringResId);
    }
}
