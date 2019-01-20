package com.youzi.service.di;

import android.content.Context;


import com.youzi.framework.common.Config;
import com.youzi.framework.common.ui.IUIProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zzw on 2018/1/11
 */

@Module(includes = {ApiModule.class})
public class AppModule {
    @Provides
    @Singleton
    public static Context provideApplicationContext() {
        return Config.getAppContext();
    }

    @Provides
    @Singleton
    public static IUIProvider provideIUIProvider() {
        return Config.getUiProvider();
    }
}
