package com.youzi.framework.common.ui.dialog.i;

import android.support.annotation.StringRes;
import android.view.View;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public interface IAlertDialog extends IDialog{
    void setTitle(CharSequence title);
    void setTitle(@StringRes int title);
    void setContent(CharSequence content);
    void setContent(@StringRes int content);

    void setPositiveButton(CharSequence buttonText, View.OnClickListener onClickListener);
    void setPositiveButton(@StringRes int buttonText, View.OnClickListener onClickListener);
    void setNegativeButton(CharSequence buttonText, View.OnClickListener onClickListener);
    void setNegativeButton(@StringRes int buttonText, View.OnClickListener onClickListener);
}
