package com.youzi.youziwallpaper.app.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youzi.framework.base.BaseMvpActivity;
import com.youzi.framework.common.util.login.event.LoginEvent;
import com.youzi.framework.common.util.utils.FragmentHelper;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.MainActivityContract;
import com.youzi.youziwallpaper.app.ui.fragments.FindFragment;
import com.youzi.youziwallpaper.app.ui.fragments.SortFragment;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseMvpActivity<MainActivityContract.Presenter> implements MainActivityContract.View {
    @BindView(R.id.line_find)
    View lineFind;
    @BindView(R.id.line_category)
    View lineCategory;
    @BindView(R.id.iv_bottom_center)
    ImageView ivBottomCenter;

    private FragmentHelper fragmentHelper;
    private FindFragment findFragment;
    private SortFragment sortFragment;

    private int nowPos = -1;

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected View provideLayoutView() {
        return inflate(R.layout.activity_main);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getToolbar().setVisible(View.GONE);
    }



    @SuppressLint("CheckResult")
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        new RxPermissions(this)
                .request(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE})
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {

                        }
                    }
                });

        switchFragment(0);
        mPresenter.checkToken();
    }

    @OnClick({R.id.rl_find, R.id.rl_category, R.id.iv_bottom_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_find:
                switchFragment(0);
                break;
            case R.id.rl_category:
                switchFragment(1);
                break;
            case R.id.iv_bottom_center:
                break;
        }
    }

    private void switchFragment(int pos) {
        if (nowPos == pos) return;

        if (fragmentHelper == null) {
            fragmentHelper = new FragmentHelper(getSupportFragmentManager(), R.id.frame_layout);
        }

        this.nowPos = pos;
        if (pos == 0) {
            if (findFragment == null) {
                findFragment = FindFragment.newInstance();
            }
            fragmentHelper.switchFragment(findFragment);

            lineFind.setVisibility(View.VISIBLE);
            lineCategory.setVisibility(View.GONE);

        } else {
            if (sortFragment == null) {
                sortFragment = SortFragment.newInstance();
            }
            fragmentHelper.switchFragment(sortFragment);

            lineFind.setVisibility(View.GONE);
            lineCategory.setVisibility(View.VISIBLE);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login(LoginEvent event) {
        mPresenter.checkToken();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}