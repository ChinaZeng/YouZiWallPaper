package com.youzi.framework.uiimpl01.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.youzi.framework.common.ui.dialog.i.IProgressDialog;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public class ProgressDialogImpl extends ProgressDialog implements IProgressDialog {
    public ProgressDialogImpl(Context context) {
        super(context);
    }

    public ProgressDialogImpl(Context context, int theme) {
        super(context, theme);
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

    @Override
    public void setContent(CharSequence content) {
        super.setMessage(content);
    }

    @Override
    public void setContent(int content) {
        super.setMessage(getContext().getResources().getString(content));
    }

    @Override
    public void setPositiveButton(CharSequence buttonText, final View.OnClickListener onClickListener) {
        super.setButton(AlertDialog.BUTTON_POSITIVE, buttonText, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (onClickListener != null) {
                    onClickListener.onClick(null);
                }
            }
        });
    }

    @Override
    public void setPositiveButton(int buttonText, View.OnClickListener onClickListener) {
        setPositiveButton(getContext().getResources().getString(buttonText), onClickListener);
    }

    @Override
    public void setNegativeButton(CharSequence buttonText, final View.OnClickListener onClickListener) {
        super.setButton(AlertDialog.BUTTON_NEGATIVE, buttonText, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (onClickListener != null) {
                    onClickListener.onClick(null);
                }
            }
        });
    }

    @Override
    public void setNegativeButton(int buttonText, View.OnClickListener onClickListener) {
        setNegativeButton(getContext().getResources().getString(buttonText), onClickListener);
    }
}
