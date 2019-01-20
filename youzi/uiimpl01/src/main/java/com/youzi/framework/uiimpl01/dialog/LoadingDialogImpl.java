package com.youzi.framework.uiimpl01.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.youzi.framework.common.ui.dialog.i.ILoadingDialog;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public class LoadingDialogImpl extends ProgressDialog implements ILoadingDialog {
    public LoadingDialogImpl(Context context) {
        super(context);
    }

    @Override
    public void setMessage(int messageResId) {
        super.setMessage(getContext().getResources().getString(messageResId));
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
