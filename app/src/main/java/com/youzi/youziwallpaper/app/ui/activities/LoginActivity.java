package com.youzi.youziwallpaper.app.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youzi.framework.base.BaseMvpActivity;
import com.youzi.framework.common.util.log.LogUtil;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.LoginActivityContract;
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


    private static final String FROM_KEY = "from_key";

    private String from;

    /**
     * @param context
     * @param fromClass
     */
    public static void open(Context context, String fromClass) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(FROM_KEY, fromClass);
        context.startActivity(intent);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        from = getIntent().getStringExtra(FROM_KEY);
    }

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
                    mTencent = Tencent.createInstance("1106120807", this);

                mTencent.login(this, "all", qqLoginListener);
                break;
            case R.id.iv_wx:
                loginOk();
                break;
            case R.id.iv_wb:
                loginOk();
                break;
        }
    }


    @Override
    public void loginOk() {
        finishPage();
    }


    BaseUiListener qqLoginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };

    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            LogUtil.d(jsonObject.toString());
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }

        } catch (Exception e) {
        }
    }

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {
                    mPresenter.saveQQLoginInfo(response, mTencent.getOpenId());
                }

                @Override
                public void onCancel() {

                }
            };
            UserInfo mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

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
            Tencent.onActivityResultData(requestCode, resultCode, data, qqLoginListener);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
