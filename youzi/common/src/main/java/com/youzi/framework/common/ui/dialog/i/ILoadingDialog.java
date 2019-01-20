package com.youzi.framework.common.ui.dialog.i;

import android.support.annotation.StringRes;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public interface ILoadingDialog extends IDialog {
    void setMessage(CharSequence message);

    void setMessage(@StringRes int messageResId);

    void setCancelable(boolean cancel);
}
