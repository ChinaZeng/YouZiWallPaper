package com.youzi.youziwallpaper.app.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
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

import org.json.JSONException;
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

                mTencent.login(this, "all", qqLoginListener);
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
    public void toMain() {
        startActivity(MainActivity.class);
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
                    /*
                    {
                        "ret":0,
                            "msg":"",
                            "is_lost":0,
                            "nickname":"⭕_⭕",
                            "gender":"男",
                            "province":"四川",
                            "city":"成都",
                            "year":"1899",
                            "constellation":"",
                            "figureurl":"http://qzapp.qlogo.cn/qzapp/101573968/A892446BF4192DF48E9650E82BEEA736/30",
                            "figureurl_1":"http://qzapp.qlogo.cn/qzapp/101573968/A892446BF4192DF48E9650E82BEEA736/50",
                            "figureurl_2":"http://qzapp.qlogo.cn/qzapp/101573968/A892446BF4192DF48E9650E82BEEA736/100",
                            "figureurl_qq_1":"http://thirdqq.qlogo.cn/g?b=oidb&k=VbCKAEJ83Wv7Yxm7Tqa5Pg&s=40",
                            "figureurl_qq_2":"http://thirdqq.qlogo.cn/g?b=oidb&k=VbCKAEJ83Wv7Yxm7Tqa5Pg&s=100",
                            "figureurl_qq":"http://thirdqq.qlogo.cn/g?b=oidb&k=VbCKAEJ83Wv7Yxm7Tqa5Pg&s=140",
                            "figureurl_type":"1",
                            "is_yellow_vip":"0",
                            "vip":"0",
                            "yellow_vip_level":"0",
                            "level":"0",
                            "is_yellow_year_vip":"0"
                    }
                    */
                    LogUtil.d(response.toString());
                    toMain();
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
