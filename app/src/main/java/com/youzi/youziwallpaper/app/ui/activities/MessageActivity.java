package com.youzi.youziwallpaper.app.ui.activities;

import android.view.View;

import com.youzi.framework.base.BaseMvpActivity;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.MessageActivityContract;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import butterknife.OnClick;

public class MessageActivity extends BaseMvpActivity<MessageActivityContract.Presenter> implements MessageActivityContract.View {
    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected View provideLayoutView() {
        return inflate(R.layout.activity_message);
    }


    @OnClick({R.id.tv_follow, R.id.tv_zan, R.id.tv_at, R.id.tv_comment, R.id.tv_system_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_follow:
                break;
            case R.id.tv_zan:
                break;
            case R.id.tv_at:
                break;
            case R.id.tv_comment:
                break;
            case R.id.tv_system_message:
                break;
        }
    }
}