package com.youzi.youziwallpaper.app.mvp.presenters;

import android.os.UserManager;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.framework.common.util.log.LogUtil;
import com.youzi.framework.common.util.login.LoginManager;
import com.youzi.service.api.RespObserver;
import com.youzi.service.api.apis.ClientApi;
import com.youzi.service.api.transformers.ResponseTransformer;
import com.youzi.youziwallpaper.app.bean.UserInfoBean;
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
    public void saveQQLoginInfo(Object response, String openId) {
        try {
            LogUtil.d(response.toString());
            String nikeName = null;
            String headerUrl = null;
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
            JSONObject jsonObject = (JSONObject) response;
            if (jsonObject.has("nickname")) {
                nikeName = jsonObject.getString("nickname");
            }
            if (jsonObject.has("figureurl_qq")) {
                headerUrl = jsonObject.getString("figureurl_qq");
            }

            saveLoginInfo(0, nikeName, openId, headerUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveWXLoginInfo(Object response) {

    }

    /**
     * @param type      0 qq 1wx 2wb
     * @param nikeName
     * @param userId
     * @param headerUrl
     */
    private void saveLoginInfo(int type, String nikeName, String userId, String headerUrl) {
        mClientApi.loginPostThirdParty(String.valueOf(type), userId, nikeName)
                .compose(mView.newDialogLoadingTransformer())
                .compose(ResponseTransformer.create())
                .subscribe(new RespObserver<String>() {
                    @Override
                    public void onSuccess(String token) {

                        UserInfoBean userInfoBean = new UserInfoBean();
                        userInfoBean.nikeName = nikeName;
                        userInfoBean.userId = userId;
                        userInfoBean.headerUrl = headerUrl;
                        userInfoBean.token = token;
                        userInfoBean.loginType = UserInfoBean.LoginType.parserType(type);
                        LoginManager.getInstance().signIn(userInfoBean);

                        mView.loginOk();
                    }
                });
    }
}
