package com.youzi.framework.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.youzi.framework.base.mvp.IBasePagingView;
import com.youzi.framework.common.ui.ILoadingHelper;
import com.youzi.framework.common.ui.IToast;
import com.youzi.framework.common.ui.IUIProvider;
import com.youzi.framework.common.ui.dialog.i.ILoadingDialog;
import com.youzi.framework.common.ui.refresh.IRefreshLayout;
import com.youzi.framework.common.ui.toolbar.IToolbar;
import com.youzi.framework.common.util.autoparam.AutoParamCompat;
import com.youzi.framework.common.util.lifecycle.rx2.LifecyleProviderCompat;
import com.youzi.framework.common.util.paging.v2.Paging;
import com.youzi.framework.common.util.systembar.BarCompat;
import com.youzi.framework.common.util.transformers.rx2.IDialogTransformer;
import com.youzi.framework.common.util.transformers.rx2.ILoadingTransformer;
import com.youzi.framework.common.util.utils.InputMethodSoftUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.ObservableTransformer;

/**
 * Created by LuoHaifeng on 2018/1/31 0031.
 * Email:496349136@qq.com
 */

public abstract class BaseActivity extends AppCompatActivity implements IBasePagingView{
    @Inject
    protected IUIProvider mUiProvider;
    private FrameLayout mContentContainer;
    private IToolbar mToolbar;
    private BarCompat barCompat;
    private BarCompat.Builder barBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        daggerInject();
        //自动注入参数
        AutoParamCompat.injectValue(this, savedInstanceState, getIntent().getExtras());
        super.onCreate(savedInstanceState);
        //初始化布局内容
        ViewGroup root = (ViewGroup) getLayoutInflater().inflate(R.layout.base_activity_layout, null);
        super.setContentView(root);
        mContentContainer = findViewById(R.id.base_content_container);
        mToolbar = findViewById(R.id.base_default_toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar.getToolbar());
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mToolbar.setBackButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            mToolbar.setTitle(getTitle());
        }
        View layoutView = provideLayoutView();
        if (layoutView != null) {
            mContentContainer.addView(layoutView);
        }
        ButterKnife.bind(this);

        //初始化系统状态栏样式
        initSystemBar();
    }

    protected abstract void daggerInject();

    protected void initSystemBar() {
        TypedArray typedArray = getTheme().obtainStyledAttributes(new int[]{R.attr.colorPrimary, com.youzi.framework.common.R.attr.statusBarTextColorDarkMode});
        int statusBarColor = typedArray.getColor(0, Color.BLACK);
        boolean textDarkMode = typedArray.getBoolean(1, false);
        typedArray.recycle();
        barBuilder = BarCompat.newBuilder()
                .setStatusBarColor(statusBarColor)
                .setStatusBarTextColorDarkMode(textDarkMode)
                .setStatusBarPadding(true);
        barCompat = barBuilder.build(this);
        //update by customer
        BarCompat.Builder oldBarBuilder = barBuilder == null ? BarCompat.newBuilder() : barBuilder;
        BarCompat.Builder newBarBuilder = onUpdateSystemBar(oldBarBuilder);
        if (newBarBuilder != null) {
            barCompat.setBuilder(newBarBuilder).update();
            this.barBuilder = newBarBuilder;
        }
    }

    protected void setToolbarOverFlowStyle() {
        setToolbarOverFlowStyle(true);
    }

    protected void setToolbarOverFlowStyle(boolean flow) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mContentContainer.getLayoutParams();
        if (flow) {
            layoutParams.topToTop = R.id.base_activity_root;
            layoutParams.topToBottom = -1;
        } else {
            layoutParams.topToTop = -1;
            layoutParams.topToBottom = R.id.base_default_toolbar;
        }
        mContentContainer.setLayoutParams(layoutParams);
    }

    protected void setToolbarFitSystemWindowPadding(boolean padding) {
        if (!BarCompat.isSupportCurrentVersion()) {
            return;
        }

        View toolbarView = (View) getToolbar();
        if (toolbarView == null) {
            return;
        }

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) toolbarView.getLayoutParams();
        if (padding) {
            layoutParams.topMargin = BarCompat.getStatusBarHeight(getResources());
        } else {
            layoutParams.topMargin = 0;
        }
        toolbarView.setLayoutParams(layoutParams);
    }

    protected void setToolbarVisible(int visible) {
        IToolbar toolbar = getToolbar();
        if (toolbar != null) {
            if (toolbar instanceof View) {
                ((View) toolbar).setVisibility(visible);
            } else {
                Toolbar realToolbar = toolbar.getToolbar();
                if (realToolbar != null) {
                    realToolbar.setVisibility(visible);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (BaseConfig.umengAnalyticsEnable && umengAnalyticsEnable()) {
//            if (BaseConfig.umengAnalyticsPageEnable && umengAnalyticsPageEnable()) {
//                MobclickAgent.onPageStart(umengAnalyticsPageName());
//            }
//            MobclickAgent.onResume(this);
//        }
    }


    @Override
    protected void onPause() {
        super.onPause();
//        if (BaseConfig.umengAnalyticsEnable && umengAnalyticsEnable()) {
//            if (BaseConfig.umengAnalyticsPageEnable && umengAnalyticsPageEnable()) {
//                MobclickAgent.onPageEnd(umengAnalyticsPageName());
//            }
//            MobclickAgent.onPause(this);
//        }
    }

    /**
     * 1.需要BaseApplication中初始化友盟 并 开启页面时长统计
     *
     * @return 当前APP的时长统计是否开启
     */
    protected boolean umengAnalyticsEnable() {
        return true;
    }

    /**
     * 1.需要BaseApplication中初始化友盟 并 开启页面时长统计
     * 2.需要当前页面统计时长开启{@link #umengAnalyticsEnable()}
     *
     * @return 当前页面的时长统计是否开启
     */
    protected boolean umengAnalyticsPageEnable() {
        return true;
    }

    protected String umengAnalyticsPageName() {
        return this.getClass().getSimpleName();
    }

    protected BarCompat.Builder onUpdateSystemBar(BarCompat.Builder builder) {
        return null;
    }

    public void setStatusBarPadding(boolean isPadding) {
        barCompat.getBuilder().setStatusBarPadding(isPadding);
        barCompat.update();
    }

    public void setStatusBarColor(@ColorInt int color) {
        barCompat.getBuilder().setStatusBarColor(color);
        barCompat.update();
    }

    public void setStatusBarTextMode(boolean isDarkMode) {
        barCompat.getBuilder().setStatusBarTextColorDarkMode(isDarkMode);
        barCompat.update();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //自动保存参数
        AutoParamCompat.saveValues(this, outState);
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, null);
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentContainer.removeAllViews();
        addContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        if (view != null) {
            mContentContainer.addView(view, params);
        }
    }

    @Override
    public void setTitle(int titleId) {
        if (mToolbar != null) {
            mToolbar.setTitle(titleId);
        } else {
            super.setTitle(titleId);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        } else {
            super.setTitle(title);
        }
    }

    public View inflate(@LayoutRes int layoutId) {
        return getLayoutInflater().inflate(layoutId, null);
    }

    protected abstract View provideLayoutView();

    public Context getContext() {
        return this;
    }

    protected void startActivity(Class<? extends Activity> targetClass) {
        startActivity(new Intent(this, targetClass));
    }

    protected void startActivityForResult(Class<? extends Activity> targetClass, int requestCode) {
        startActivityForResult(new Intent(this, targetClass), requestCode);
    }

    @Override
    public void finishPage() {
        finish();
    }

    @Override
    public IToast provideToast() {
        return mUiProvider.provideToast();
    }

    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return LifecyleProviderCompat.getActivityLifcycleProvider(this).bindUntilEvent(ActivityEvent.DESTROY);
    }

    @Override
    public <S> ObservableTransformer<S, S> newDialogLoadingTransformer(String... params) {
        ILoadingDialog loadingDialog = mUiProvider.newLoadingDialog(getContext());
        loadingDialog.setCancelable(false);
        if (params.length > 0) {
            loadingDialog.setMessage(params[0]);
        } else {
            loadingDialog.setMessage(R.string.base_loading);
        }
        return new IDialogTransformer<>(loadingDialog, this);
    }

    @Override
    public <S> ObservableTransformer<S, S> newContentLoadingTransformer(ILoadingHelper.OnRetryListener onRetryListener) {
        return new ILoadingTransformer<>(provideLoadingHelper().setRetryListener(onRetryListener), this);
    }

    @Override
    public ILoadingHelper provideLoadingHelper() {
        return mUiProvider.provideLoadingHelper(mContentContainer);
    }

    @Override
    public IRefreshLayout provideRefreshLayout() {
        return null;
    }

    @Override
    public <T> Paging<T> providePagingV2() {
        Paging<T> paging = Paging.create();
        return paging.bind(new Paging.SimplePagingView(provideLoadingHelper(), provideRefreshLayout(), this));
    }

    @Override
    public Context provideContext() {
        return this;
    }

    public IToolbar getToolbar() {
        return mToolbar;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        InputMethodSoftUtil.dispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    public BarCompat getBarCompat() {
        return barCompat;
    }
}
