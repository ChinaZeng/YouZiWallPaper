package com.youzi.youziwallpaper.app.mvp.presenters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.framework.common.util.log.LogUtil;
import com.youzi.service.api.apis.ClientApi;
import com.youzi.youziwallpaper.app.mvp.contracts.LoginActivityContract;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by zzw on 2019/1/11.
 * 描述:
 */
public class LoginActivityPresenter extends BasePresenter<LoginActivityContract.View> implements LoginActivityContract.Presenter {




    @Inject
    ClientApi mClientApi;

    @Inject
    public LoginActivityPresenter() {
    }


    @Override
    public void qqLogin(Activity activity) {

    }


}
