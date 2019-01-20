package com.youzi.framework.common.ui;

import android.support.annotation.StringRes;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public interface IToast {
    void showInfo(CharSequence message);

    void showInfo(@StringRes int message);

    void showWarn(CharSequence message);

    void showWarn(@StringRes int message);

    void showError(CharSequence message);

    void showError(@StringRes int message);

    void showSuccess(CharSequence message);

    void showSuccess(@StringRes int message);
}
