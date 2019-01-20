package com.youzi.framework.common.util.systembar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.LinearLayout;

/**
 * Created by LuoHaifeng on 2018/5/16 0016.
 * Email:496349136@qq.com
 */
@TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
public class NoPaddingLinearLayout extends LinearLayoutCompat {
    boolean isFix = false;

    public NoPaddingLinearLayout(Context context) {
        super(context);
    }

    public NoPaddingLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoPaddingLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        if (child.getId() == Window.ID_ANDROID_CONTENT) {
            if (isFix) {
                heightUsed -= BarCompat.getStatusBarHeight(getResources());
                heightUsed = Math.max(0, heightUsed);
            }
        }
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    @Override
    public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
        int top = insets.getSystemWindowInsetTop();
        int bottom = insets.getSystemWindowInsetBottom();
        int left = insets.getSystemWindowInsetLeft();
        int right = insets.getSystemWindowInsetRight();
        if (top != 0 && top >= BarCompat.getStatusBarHeight(getResources())) {
            top -= BarCompat.getStatusBarHeight(getResources());
        }
        return super.dispatchApplyWindowInsets(insets.replaceSystemWindowInsets(left, top, right, bottom));
    }
}
