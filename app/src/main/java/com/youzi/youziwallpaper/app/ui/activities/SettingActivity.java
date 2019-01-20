package com.youzi.youziwallpaper.app.ui.activities;

import android.view.View;
import android.widget.TextView;

import com.youzi.framework.base.BaseMvpActivity;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.SettingActivityContract;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseMvpActivity<SettingActivityContract.Presenter> implements SettingActivityContract.View {
    @BindView(R.id.tv_cache_num)
    TextView tvCacheNum;

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected View provideLayoutView() {
        return inflate(R.layout.activity_setting);
    }


    @OnClick({R.id.tv_account, R.id.tv_edit_person_info, R.id.ll_clean_cache, R.id.tv_appeal, R.id.tv_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_account:
                break;
            case R.id.tv_edit_person_info:
                break;
            case R.id.ll_clean_cache:
                break;
            case R.id.tv_appeal:
                break;
            case R.id.tv_about:
                break;
        }
    }
}