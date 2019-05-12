package com.youzi.youziwallpaper.di;


import com.youzi.service.di.AppModule;
import com.youzi.youziwallpaper.app.ui.activities.EditPersonInfoActivity;
import com.youzi.youziwallpaper.app.ui.activities.LoginActivity;
import com.youzi.youziwallpaper.app.ui.activities.MainActivity;
import com.youzi.youziwallpaper.app.ui.activities.MessageActivity;
import com.youzi.youziwallpaper.app.ui.activities.SettingActivity;
import com.youzi.youziwallpaper.app.ui.activities.SortVideoListActivity;
import com.youzi.youziwallpaper.app.ui.activities.SplashActivity;
import com.youzi.youziwallpaper.app.ui.activities.VideoDetailActivity;
import com.youzi.youziwallpaper.app.ui.fragments.AdFragment;
import com.youzi.youziwallpaper.app.ui.fragments.FindFragment;
import com.youzi.youziwallpaper.app.ui.fragments.FollowFragment;
import com.youzi.youziwallpaper.app.ui.fragments.SortFragment;
import com.youzi.youziwallpaper.app.ui.fragments.RecommendFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by LuoHaifeng on 2018/1/31 0031.
 * Email:496349136@qq.com
 */
@Singleton
@Component(modules = {AppModule.class, ActivityModule.class, FragmentModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(SplashActivity activity);

    void inject(MessageActivity activity);

    void inject(EditPersonInfoActivity activity);

    void inject(SettingActivity activity);

    void inject(SortVideoListActivity activity);

    void inject(VideoDetailActivity activity);


    //---------------------fragment-----------------------------
    void inject(AdFragment fragment);

    void inject(FindFragment fragment);

    void inject(RecommendFragment fragment);

    void inject(SortFragment fragment);

    void inject(FollowFragment fragment);
}
