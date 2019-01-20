package com.youzi.framework.base;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;
import com.youzi.framework.common.util.lifecycle.rx2.LifecyleProviderCompat;

/**
 * Created by LuoHaifeng on 2018/1/31 0031.
 * Email:496349136@qq.com
 */

public abstract class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        quickInit(this);
    }

    public static void quickInit(Application application) {
        //配置TBS
        QbSdk.initX5Environment(application, null);
        //RxLifecycle辅助插件
        LifecyleProviderCompat.installSupport(application);
    }

    public static void openUmengAnalytics(Application application, boolean isDebug, boolean isOpenPageAnalytics) {
        BaseConfig.umengAnalyticsEnable = true;
        BaseConfig.umengAnalyticsPageEnable = isOpenPageAnalytics;
//        UMConfigure.init(application, UMConfigure.DEVICE_TYPE_PHONE, null);
//        MobclickAgent.setScenarioType(application, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        UMConfigure.setLogEnabled(isDebug);//debug模式打印日志
//        UMConfigure.setEncryptEnabled(!isDebug);//debug模式不加密
    }

    public static void openUmengAnalytics(Application application, String appKey, String channel, boolean isOpenPageAnalytics, boolean isDebug) {
        BaseConfig.umengAnalyticsEnable = true;
        BaseConfig.umengAnalyticsPageEnable = isOpenPageAnalytics;
//        UMConfigure.init(application, appKey, channel, UMConfigure.DEVICE_TYPE_PHONE, null);
//        MobclickAgent.setScenarioType(application, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        UMConfigure.setLogEnabled(isDebug);//debug模式打印日志
//        UMConfigure.setEncryptEnabled(!isDebug);//debug模式不加密
    }
}
