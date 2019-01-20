package com.youzi.youziwallpaper.app.ui.fragments;

import com.youzi.framework.base.BaseMvpFragment;

import android.view.View;

import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.AdFragmentContract;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

public class AdFragment extends BaseMvpFragment<AdFragmentContract.Presenter> implements AdFragmentContract.View {
    public static AdFragment newInstance(){
        return new AdFragment();
    }

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected View provideLayoutView() {
        return inflate(R.layout.fragment_ad);
    }
}