package com.youzi.framework.common.ui.toolbar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017\11\15 0015.
 */

abstract class AbstractToolbar extends FrameLayout implements IToolbar {
    private IToolbar realToolbar;

    public AbstractToolbar(Context context) {
        super(context);
        init();
    }

    public AbstractToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AbstractToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        realToolbar = provideRealToolbar();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView((View) realToolbar, layoutParams);
    }

    protected abstract IToolbar provideRealToolbar();

    @Override
    public TextView getTitleView() {
        return realToolbar.getTitleView();
    }

    @Override
    public void setTitle(CharSequence title) {
        realToolbar.setTitle(title);
    }

    @Override
    public void setTitle(int title) {
        realToolbar.setTitle(title);
    }

    @Override
    public void setVisible(int visible) {
        setVisibility(visible);
    }

    @Override
    public void addRightButton(View view) {
        realToolbar.addRightButton(view);
    }

    @Override
    public void addLeftButton(View view) {
        realToolbar.addLeftButton(view);
    }

    @Override
    public void setBackButtonEnable(boolean enable) {
        realToolbar.setBackButtonEnable(enable);
    }

    @Override
    public Toolbar getToolbar() {
        return realToolbar.getToolbar();
    }

    @Override
    public void showBottomLine(boolean isShow) {
        realToolbar.showBottomLine(isShow);
    }

    @Override
    public void setBackButtonClickListener(OnClickListener clickListener) {
        realToolbar.setBackButtonClickListener(clickListener);
    }

    @Override
    public View getView() {
        return this;
    }
}
