package com.youzi.framework.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
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

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.ObservableTransformer;

/**
 * Created by LuoHaifeng on 2018/1/31 0031.
 * Email:496349136@qq.com
 */

public abstract class BaseFragment extends Fragment implements IBasePagingView {
    @Inject
    protected IUIProvider mUiProvider;
    private FrameLayout contentContainer;
    private IToolbar toolbar;

    private BarCompat.Builder barBuilder;
    protected boolean initAndVisibleState = false;
    protected boolean viewCreated = false;

    @Override
    public void onAttach(Context context) {
        daggerInject();
        super.onAttach(context);
    }

    protected abstract void daggerInject();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AutoParamCompat.injectValue(this, savedInstanceState, getArguments());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AutoParamCompat.saveValues(this, outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //初始化布局内容
        ViewGroup root = (ViewGroup) getLayoutInflater().inflate(R.layout.base_fragment_layout, null);
        contentContainer = root.findViewById(R.id.base_fragment_content_container);
        toolbar = root.findViewById(R.id.base_fragment_default_toolbar);
        toolbar.setBackButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });
        toolbar.setVisible(View.GONE);
        View layoutView = provideLayoutView();
        if (layoutView != null) {
            contentContainer.addView(layoutView);
        }
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewCreated = true;
        tryUpdateVisibleStateListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.viewCreated = false;
        tryUpdateVisibleStateListening();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        tryUpdateVisibleStateListening();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        tryUpdateVisibleStateListening();
    }

    public void tryUpdateVisibleStateListening() {
        boolean visibleState = getInitedAndVisibleState();
        if (this.initAndVisibleState != visibleState) {
            this.initAndVisibleState = visibleState;
            if (visibleState) {
                onPageEnter();
            } else {
                onPageLeave();
            }

            //状态发生改变时去通知子fragment更新状态
            FragmentManager fragmentManager = getChildFragmentManager();
            List<Fragment> fragments = fragmentManager.getFragments();
            if (fragments.size() > 0) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof BaseFragment) {
                        ((BaseFragment) fragment).tryUpdateVisibleStateListening();
                    }
                }
            }
        }
    }

    protected void onPageEnter() {
        updateSystemBar();
        System.out.println(">>>--------------------" + this + "----------------PageEnter");
    }

    protected void onPageLeave() {
        System.out.println(">>>--------------------" + this + "----------------PageLeave");
    }

    public boolean getInitedAndVisibleState() {
        boolean parentVisible = true;
        Fragment parent = getParentFragment();
        if(parent != null){
            if(parent instanceof BaseFragment){
                parentVisible = ((BaseFragment) parent).getInitedAndVisibleState();
            }else {
                parentVisible = parent.isVisible();
            }
        }

        return getUserVisibleHint() && !isHidden() && viewCreated && isResumed() && parentVisible;
    }

    protected void setToolbarOverFlowStyle(boolean flow) {
        if (contentContainer != null) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) contentContainer.getLayoutParams();
            if (flow) {
                layoutParams.topToTop = R.id.base_fragment_root;
                layoutParams.topToBottom = -1;
            } else {
                layoutParams.topToTop = -1;
                layoutParams.topToBottom = R.id.base_fragment_default_toolbar;
            }
            contentContainer.setLayoutParams(layoutParams);
        }
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

    protected void updateSystemBar() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            BarCompat barCompat = ((BaseActivity) activity).getBarCompat();
            if (barCompat != null) {
                BarCompat.Builder oldBarBuilder = barBuilder == null ? barCompat.newBuilderWith() : barBuilder;
                BarCompat.Builder newBarBuilder = onUpdateSystemBar(oldBarBuilder);
                if (newBarBuilder != null) {
                    System.out.println(">>>" + this + "-------- updated status bar");
                    barCompat.setBuilder(newBarBuilder).update();
                    this.barBuilder = newBarBuilder;
                }
            }
        }
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

    protected BarCompat.Builder onUpdateSystemBar(BarCompat.Builder builder) {
        return null;
    }

    public void setStatusBarPadding(boolean isPadding) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setStatusBarPadding(isPadding);
        }
    }

    public void setStatusBarColor(@ColorInt int color) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setStatusBarColor(color);
        }
    }

    public void setStatusBarTextMode(boolean isDarkMode) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setStatusBarTextMode(isDarkMode);
        }
    }

    protected abstract View provideLayoutView();

    @Override
    public Context provideContext() {
        return getContext();
    }

    @Override
    public void finishPage() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    public View inflate(@LayoutRes int layoutId) {
        return getLayoutInflater().inflate(layoutId, null);
    }

    protected void startActivity(Class<? extends Activity> targetClass) {
        startActivity(new Intent(getContext(), targetClass));
    }

    protected void startActivityForResult(Class<? extends Activity> targetClass, int requestCode) {
        startActivityForResult(new Intent(getContext(), targetClass), requestCode);
    }

    public IToolbar getToolbar() {
        return toolbar;
    }

    public void setToolBarEnable(boolean enable) {
        if (enable) {
            getToolbar().setVisible(View.VISIBLE);
            FragmentActivity activity = getActivity();
            if (activity instanceof AppCompatActivity) {
                ((AppCompatActivity) activity).setSupportActionBar(getToolbar().getToolbar());
            }
        } else {
            getToolbar().setVisible(View.GONE);
        }
    }

    @Override
    public IToast provideToast() {
        return mUiProvider.provideToast();
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
    public IRefreshLayout provideRefreshLayout() {
        return null;
    }

    @Override
    public ILoadingHelper provideLoadingHelper() {
        return mUiProvider.provideLoadingHelper(contentContainer);
    }

    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return LifecyleProviderCompat.getFragmentLifecycleProvider(this).bindUntilEvent(FragmentEvent.DESTROY_VIEW);
    }

    @Override
    public <T> Paging<T> providePagingV2() {
        Paging<T> paging = Paging.create();
        return paging.bind(new Paging.SimplePagingView(provideLoadingHelper(), provideRefreshLayout(), this));
    }

    @Override
    public void onResume() {
        super.onResume();
        tryUpdateVisibleStateListening();
//        if (BaseConfig.umengAnalyticsEnable && umengAnalyticsEnable()) {
//            if (BaseConfig.umengAnalyticsPageEnable && umengAnalyticsPageEnable()) {
//                MobclickAgent.onPageStart(umengAnalyticsPageName());
//            }
//        }
    }


    @Override
    public void onPause() {
        super.onPause();
        tryUpdateVisibleStateListening();
//        if (BaseConfig.umengAnalyticsEnable && umengAnalyticsEnable()) {
//            if (BaseConfig.umengAnalyticsPageEnable && umengAnalyticsPageEnable()) {
//                MobclickAgent.onPageEnd(umengAnalyticsPageName());
//            }
//        }
    }

    /**
     * 1.需要BaseApplication中初始化友盟 并 开启页面时长统计
     *
     * @return 当前APP的时长统计是否开启
     */
    protected boolean umengAnalyticsEnable() {
        return getActivity() instanceof BaseActivity && ((BaseActivity) getActivity()).umengAnalyticsEnable();
    }

    /**
     * 1.需要BaseApplication中初始化友盟 并 开启页面时长统计
     * 2.需要当前页面统计时长开启{@link #umengAnalyticsEnable()}
     *
     * @return 当前页面的时长统计是否开启
     */
    protected boolean umengAnalyticsPageEnable() {
        return getActivity() instanceof BaseActivity && ((BaseActivity) getActivity()).umengAnalyticsPageEnable();
    }

    protected String umengAnalyticsPageName() {
        return this.getClass().getSimpleName();
    }
}
