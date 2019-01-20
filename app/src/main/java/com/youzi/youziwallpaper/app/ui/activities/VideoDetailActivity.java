package com.youzi.youziwallpaper.app.ui.activities;

import com.youzi.framework.base.BaseMvpActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.youzi.framework.common.util.systembar.BarCompat;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.VideoDetailActivityContract;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

public class VideoDetailActivity extends BaseMvpActivity<VideoDetailActivityContract.Presenter> implements VideoDetailActivityContract.View {

    public static void open(Context context) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setTitle(" ");
        //设置背景颜色
        getToolbar().getView().setBackgroundColor(Color.TRANSPARENT);
        //设置toolbar上方的padding
        setToolbarFitSystemWindowPadding(true);
        //设置内容区域的对照关系，让内容区域不要让显示在toolbar下方
        setToolbarOverFlowStyle(true);
    }

    @Override
    protected BarCompat.Builder onUpdateSystemBar(BarCompat.Builder builder) {
        //设置沉浸式
        builder.setStatusBarPadding(false);
        return builder;
    }

    @Override
    protected View provideLayoutView() {
        return inflate(R.layout.activity_video_detail);
    }
}