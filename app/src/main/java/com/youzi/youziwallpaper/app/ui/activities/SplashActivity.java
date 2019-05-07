package com.youzi.youziwallpaper.app.ui.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.youzi.framework.base.BaseMvpActivity;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.SplashActivityContract;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

public class SplashActivity extends BaseMvpActivity<SplashActivityContract.Presenter> implements SplashActivityContract.View {
    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected View provideLayoutView() {
        return inflate(R.layout.activity_splash);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarVisible(View.GONE);
    }

    @Override
    public void toMain() {
        startActivity(MainActivity.class);
        finish();
    }
}