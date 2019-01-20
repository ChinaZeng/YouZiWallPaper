package com.youzi.framework.common.ui.dialog.i;

import com.youzi.framework.common.ui.dialog.OnCancelListener;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public interface IDialog {
    void show();

    void dismiss();

    void cancel();

    boolean isShowing();

    void setOnCancelListener(OnCancelListener onCancelListener);

    void setCancelable(boolean cancelable);
}
