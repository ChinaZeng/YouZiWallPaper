package com.youzi.youziwallpaper;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.youzi.framework.base.BaseApplication;
import com.youzi.framework.common.Config;
import com.youzi.framework.common.error.DefaultErrorHandler;
import com.youzi.framework.common.error.ErrorHandlerManager;
import com.youzi.framework.uiimpl01.UIProvider;
import com.youzi.service.api.ApiErrorHandler;

/**
 * Created by zzw on 2019/1/10.
 * 描述:
 */
public class YouZiWallpaperApplication  extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ErrorHandlerManager.getInstance().setErrorHandler(new ApiErrorHandler());
        Config.init(this, new UIProvider());
    }
}
