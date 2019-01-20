package com.youzi.framework.uiimpl01.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.youzi.framework.common.ui.dialog.i.IAlertDialog;
import com.youzi.framework.uiimpl01.R;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public class AlertDialogV2Impl extends AppCompatDialog implements IAlertDialog {
    private TextView tvTitle;
    private TextView tvContent;
    private TextView btnPositive;
    private TextView btnNegative;

    public AlertDialogV2Impl(@NonNull Context context) {
        super(context,R.style.uiimpl_Dialog_AlertDialog);
        init();
    }

    private void init() {
        setContentView(R.layout.uiimpl_dialog_alert);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        btnPositive = findViewById(R.id.btn_positive);
        btnNegative = findViewById(R.id.btn_negative);
        tvContent.setVisibility(View.GONE);
        btnPositive.setVisibility(View.GONE);
        btnNegative.setVisibility(View.GONE);
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getContext().getResources().getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
        tvTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setContent(CharSequence content) {
        tvContent.setText(content);
        tvContent.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setContent(int content) {
        setContent(getContext().getResources().getString(content));
    }

    @Override
    public void setPositiveButton(CharSequence buttonText, View.OnClickListener onClickListener) {
        btnPositive.setText(buttonText);
        btnPositive.setOnClickListener(onClickListener);
        btnPositive.setVisibility(TextUtils.isEmpty(buttonText) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setPositiveButton(int buttonText, View.OnClickListener onClickListener) {
        setPositiveButton(getContext().getResources().getString(buttonText), onClickListener);
    }

    @Override
    public void setNegativeButton(CharSequence buttonText, View.OnClickListener onClickListener) {
        btnNegative.setText(buttonText);
        btnNegative.setOnClickListener(onClickListener);
        btnNegative.setVisibility(TextUtils.isEmpty(buttonText) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setNegativeButton(int buttonText, View.OnClickListener onClickListener) {
        setNegativeButton(getContext().getResources().getString(buttonText), onClickListener);
    }

    @Override
    public void setOnCancelListener(final com.youzi.framework.common.ui.dialog.OnCancelListener onCancelListener) {
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (onCancelListener != null) {
                    onCancelListener.onCancel();
                }
            }
        });
    }
}
