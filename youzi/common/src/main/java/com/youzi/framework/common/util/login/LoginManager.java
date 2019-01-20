package com.youzi.framework.common.util.login;

import com.youzi.framework.common.util.login.event.LoginEvent;
import com.youzi.framework.common.util.login.event.LogoutEvent;
import com.youzi.framework.common.util.login.event.UpdateUserProfileEvent;
import com.youzi.framework.common.util.login.event.UserChangedEvent;
import com.youzi.framework.common.util.utils.SPUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

/**
 * Created by LuoHaifeng on 2017/7/6.
 * Email:496349136@qq.com
 */

public class LoginManager implements ILoginManager {
    private static final String LAST_LOGIN_USER = "LAST_LOGIN_USER";//保存登录用户名的key
    private static final String LAST_LOGIN_RESULT = "LAST_LOGIN_RESULT";//保存登录结果的key
    private SPUtil spUtil;//SharedPreferences工具类

    private LoginManager() {
        spUtil = SPUtil.getInstance("LoginManager");
    }

    public synchronized static LoginManager getInstance() {
        return new LoginManager();
    }

    @Override
    public void saveLastLoginUserName(String username) {
        spUtil.put(LAST_LOGIN_USER, username);
    }

    @Override
    public String getLastLoginUserName() {
        return spUtil.getString(LAST_LOGIN_USER);
    }

    @Override
    public void saveLastLoginResult(Serializable obj) {
        spUtil.put(LAST_LOGIN_RESULT, obj);
    }

    @Override
    public void signIn(Serializable loginResult) {
        saveLastLoginResult(loginResult);
        EventBus.getDefault().post(new LoginEvent());
        EventBus.getDefault().post(new UserChangedEvent());
    }

    @Override
    public void updateProfile(Serializable newUserInfo) {
        saveLastLoginResult(newUserInfo);
        EventBus.getDefault().post(new UpdateUserProfileEvent());
        EventBus.getDefault().post(new UserChangedEvent());
    }

    @Override
    public void signOff() {
        saveLastLoginResult(null);
        EventBus.getDefault().post(new LogoutEvent());
        EventBus.getDefault().post(new UserChangedEvent());
    }

    @Override
    public <T> T getLastLoginResult() {
        return spUtil.getSerializable(LAST_LOGIN_RESULT, null);
    }

    @Override
    public boolean isLogin() {
        return getLastLoginResult() != null;
    }
}
