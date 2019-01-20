package com.youzi.framework.common.ui.dialog.abs;

import android.content.Context;
import android.content.DialogInterface;

import com.youzi.framework.common.ui.dialog.i.IProgressDialog;

/**
 * 功能描述:进度对话框
 * Created by LuoHaifeng on 2017/7/18.
 * Email:496349136@qq.com
 */

public abstract class ProgressDialog extends AlertDialog implements IProgressDialog {

    public ProgressDialog(Context context) {
        super(context);
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public ProgressDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public abstract void setProgress(int progress);

    @Override
    public abstract void setMax(int max);
}
