package com.youzi.framework.common;

import android.content.Context;

import com.youzi.framework.common.ui.IUIProvider;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public class Config {
    private static Context appContext;
    private static IUIProvider mUiProvider;

    public static void init(Context context, IUIProvider iuiProvider) {
        appContext = context;
        mUiProvider = iuiProvider;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static IUIProvider getUiProvider() {
        return mUiProvider;
    }

    public static String getFileProviderAuthorities() {
        return getAppContext().getPackageName() + ".fileProvider";
    }
}
