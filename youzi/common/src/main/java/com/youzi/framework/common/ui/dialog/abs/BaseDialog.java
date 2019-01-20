package com.youzi.framework.common.ui.dialog.abs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatDialog;

import com.youzi.framework.common.ui.dialog.i.IDialog;

/**
 * Created by LuoHaifeng on 2017/6/28.
 * Email:496349136@qq.com
 */

public abstract class BaseDialog extends AppCompatDialog implements IDialog {
    public BaseDialog(Context context) {
        super(context);
        init();
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    protected void init() {
    }

    @Override
    public void setOnCancelListener(final com.youzi.framework.common.ui.dialog.OnCancelListener onCancelListener) {
        super.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (onCancelListener != null) {
                    onCancelListener.onCancel();
                }
            }
        });
    }
}
