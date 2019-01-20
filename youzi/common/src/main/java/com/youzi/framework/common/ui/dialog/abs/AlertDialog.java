package com.youzi.framework.common.ui.dialog.abs;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;

import com.youzi.framework.common.ui.dialog.abs.BaseDialog;
import com.youzi.framework.common.ui.dialog.i.IAlertDialog;

/**
 * 功能描述:提示类对话框(确定取消)
 * Created by LuoHaifeng on 2017/7/18.
 * Email:496349136@qq.com
 */

public abstract class AlertDialog extends BaseDialog implements IAlertDialog {

    public AlertDialog(Context context) {
        super(context);
    }

    public AlertDialog(Context context, int theme) {
        super(context, theme);
    }

    public AlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * 设置标题
     *
     * @param title 标题内容
     */
    @Override
    public abstract void setTitle(CharSequence title);

    @Override
    public void setTitle(@StringRes int title) {
        setTitle(getContext().getString(title));
    }

    /**
     * 设置对话框内容
     *
     * @param content 内容
     */
    @Override
    public abstract void setContent(CharSequence content);

    @Override
    public void setContent(@StringRes int content) {
        setContent(getContext().getString(content));
    }

    /**
     * 设置右侧按钮
     *
     * @param buttonText      按钮文字
     * @param onClickListener 点击事件
     */
    @Override
    public abstract void setPositiveButton(CharSequence buttonText, View.OnClickListener onClickListener);

    @Override
    public void setPositiveButton(@StringRes int buttonText, View.OnClickListener onClickListener) {
        setPositiveButton(getContext().getString(buttonText), onClickListener);
    }

    /**
     * 设置左侧按钮
     *
     * @param buttonText      按钮文字
     * @param onClickListener 点击事件
     */
    @Override
    public abstract void setNegativeButton(CharSequence buttonText, View.OnClickListener onClickListener);

    @Override
    public void setNegativeButton(@StringRes int buttonText, View.OnClickListener onClickListener) {
        setNegativeButton(getContext().getString(buttonText), onClickListener);
    }
}
