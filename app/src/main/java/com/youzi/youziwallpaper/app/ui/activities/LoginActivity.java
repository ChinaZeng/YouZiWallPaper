package com.youzi.youziwallpaper.app.ui.activities;

import android.view.View;
import android.widget.TextView;

import com.youzi.framework.base.BaseMvpActivity;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.LoginActivityContract;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zzw on 2019/1/11.
 * 描述:
 */
public class LoginActivity extends BaseMvpActivity<LoginActivityContract.Presenter> implements LoginActivityContract.View {

    @BindView(R.id.tv_txt)
    TextView tvTxt;

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected View provideLayoutView() {
        return inflate(R.layout.activity_login);
    }


    @OnClick({R.id.iv_qq, R.id.iv_wx, R.id.iv_wb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_qq:
                toMain();
                break;
            case R.id.iv_wx:
                toMain();
                break;
            case R.id.iv_wb:
                toMain();
                break;
        }
    }

    private void toMain(){
        startActivity(MainActivity.class);
    }
}
