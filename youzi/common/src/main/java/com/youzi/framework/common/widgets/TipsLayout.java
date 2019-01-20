package com.youzi.framework.common.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TipsLayout extends LinearLayoutCompat {
    private AppCompatTextView hintTitleView;
    private View dividerView;
    private AppCompatTextView tipsTextView;
    boolean hintTitleEnable = false;

    public TipsLayout(Context context) {
        this(context, null);
    }

    public TipsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TipsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);

        hintTitleView = new AppCompatTextView(getContext());
        hintTitleView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        hintTitleView.setTextColor(Color.parseColor("#666666"));
        hintTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);

        dividerView = new View(getContext());
        dividerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        dividerView.setBackgroundColor(Color.LTGRAY);

        tipsTextView = new AppCompatTextView(getContext());
        tipsTextView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        tipsTextView.setTextColor(Color.RED);
        tipsTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);

        this.addView(hintTitleView);
        this.addView(dividerView);
        this.addView(tipsTextView);

        hintTitleView.setVisibility(hintTitleEnable ? VISIBLE : GONE);
        tipsTextView.setVisibility(GONE);
    }

    public void setBottomLineColor(@ColorInt int color) {
        dividerView.setBackgroundColor(color);
    }

    public void setBottomLineEnable(boolean enable) {
        dividerView.setVisibility(enable ? VISIBLE : GONE);
    }

    public void setTipsTextColor(@ColorInt int color) {
        tipsTextView.setTextColor(color);
    }

    public void setTipsTextSize(int pxSize) {
        tipsTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, pxSize);
    }

    public void setTipsMarginTop(int pxMarginTop) {
        LayoutParams params = (LayoutParams) tipsTextView.getLayoutParams();
        params.topMargin = pxMarginTop;
        tipsTextView.setLayoutParams(params);
    }

    public void setHintTitleColor(@ColorInt int color) {
        hintTitleView.setTextColor(color);
    }

    public void setHintTitleTextSize(int pxSize) {
        hintTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, pxSize);
    }

    public void setHintTitleEnable(boolean enable) {
        this.hintTitleEnable = enable;
        hintTitleView.setVisibility(enable ? VISIBLE : GONE);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        int addedChildCount = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View cv = getChildAt(i);
            if (!isInnerView(cv)) {
                addedChildCount++;
            }
        }

        boolean isInnerAdd = isInnerView(child);
        if (!isInnerAdd && addedChildCount > 0) {
            throw new IllegalArgumentException("only support one direct child view");
        }
        super.addView(child, isInnerAdd ? index : 1, params);
    }

    private boolean isInnerView(View view) {
        return view == dividerView || view == tipsTextView || view == hintTitleView;
    }

    public void setTips(CharSequence tips) {
        tipsTextView.setText(tips);

        if (tips != null && !tips.toString().trim().isEmpty()) {
            tipsTextView.setVisibility(VISIBLE);
        } else {
            tipsTextView.setVisibility(GONE);
        }
    }

    public void setHintTitle(CharSequence title) {
        hintTitleView.setText(title);
    }
}
