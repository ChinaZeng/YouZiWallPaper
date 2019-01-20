package com.youzi.framework.uiimpl01.toast;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youzi.framework.common.Config;
import com.youzi.framework.uiimpl01.R;


class ToastUtil {
    private static Toast toast;
    private static Toast customToast;

    /**
     * 强大的吐司，能够连续弹的吐司
     *
     * @param text
     */
    public static void showToast(CharSequence text) {
        showToast(text, R.mipmap.uiimpl_ic_info);
    }

    public static void showToast(CharSequence text, int imgId) {
        if (customToast == null) {
            LayoutInflater inflater = LayoutInflater.from(Config.getAppContext());
            View view = inflater.inflate(R.layout.uiimpl_dialog_message, null);
            ImageView imageView = view.findViewById(R.id.iv_icon_message);
            imageView.setImageResource(imgId);
            TextView t = view.findViewById(R.id.tv_text_message);
            t.setText(text);
            customToast = new Toast(Config.getAppContext());
            customToast.setGravity(Gravity.CENTER, 0, 0);
            customToast.setDuration(Toast.LENGTH_SHORT);
            customToast.setView(view);
        } else {
            View view = customToast.getView();
            ImageView imageView = view.findViewById(R.id.iv_icon_message);
            imageView.setImageResource(imgId);
            TextView t = view.findViewById(R.id.tv_text_message);
            t.setText(text);
        }
        customToast.show();
    }
}
