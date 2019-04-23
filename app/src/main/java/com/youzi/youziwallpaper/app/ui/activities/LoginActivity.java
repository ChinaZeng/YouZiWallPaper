package com.youzi.youziwallpaper.app.ui.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youzi.framework.base.BaseMvpActivity;
import com.youzi.framework.common.util.log.LogUtil;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.LoginActivityContract;
import com.youzi.youziwallpaper.app.mvp.presenters.LoginActivityPresenter;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zzw on 2019/1/11.
 * 描述:
 */
public class LoginActivity extends BaseMvpActivity<LoginActivityContract.Presenter> implements LoginActivityContract.View {

    @BindView(R.id.tv_txt)
    TextView tvTxt;

    private Tencent mTencent;


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
                if (mTencent == null)
                    mTencent = Tencent.createInstance("101573968", this);

                mTencent.login(this, "all", iUiListener);
                break;
            case R.id.iv_wx:
                toMain();
                break;
            case R.id.iv_wb:
                toMain();
                break;
        }
    }


    @Override
    public void toMain(){
        startActivity(MainActivity.class);
        finishPage();
    }





    BaseUiListener iUiListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            initOpenidAndToken(values);
        }
    };

    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
            toMain();
        } catch (Exception e) {
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                LogUtil.d("返回为null");
                provideToast().showError("登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                LogUtil.d("返回为null");
                provideToast().showError("登录失败");
                return;
            }
            provideToast().showError("登录成功");

            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            LogUtil.d("onError: " + e.errorDetail);
            provideToast().showError("登录失败");
        }

        @Override
        public void onCancel() {
            LogUtil.d("取消登录");
           provideToast().showError("取消登录");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,iUiListener);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
