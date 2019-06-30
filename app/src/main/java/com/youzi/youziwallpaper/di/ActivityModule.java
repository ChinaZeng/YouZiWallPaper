package com.youzi.youziwallpaper.di;

import com.youzi.youziwallpaper.app.mvp.contracts.EditPersonInfoActivityContract;
import com.youzi.youziwallpaper.app.mvp.contracts.LoginActivityContract;
import com.youzi.youziwallpaper.app.mvp.contracts.MainActivityContract;
import com.youzi.youziwallpaper.app.mvp.contracts.MessageActivityContract;
import com.youzi.youziwallpaper.app.mvp.contracts.SearchActivityContract;
import com.youzi.youziwallpaper.app.mvp.contracts.SettingActivityContract;
import com.youzi.youziwallpaper.app.mvp.contracts.SortVideoListActivityContract;
import com.youzi.youziwallpaper.app.mvp.contracts.SplashActivityContract;
import com.youzi.youziwallpaper.app.mvp.contracts.VideoDetailActivityContract;
import com.youzi.youziwallpaper.app.mvp.presenters.EditPersonInfoActivityPresenter;
import com.youzi.youziwallpaper.app.mvp.presenters.LoginActivityPresenter;
import com.youzi.youziwallpaper.app.mvp.presenters.MainActivityPresenter;
import com.youzi.youziwallpaper.app.mvp.presenters.MessageActivityPresenter;
import com.youzi.youziwallpaper.app.mvp.presenters.SearchActivityPresenter;
import com.youzi.youziwallpaper.app.mvp.presenters.SettingActivityPresenter;
import com.youzi.youziwallpaper.app.mvp.presenters.SortVideoListActivityPresenter;
import com.youzi.youziwallpaper.app.mvp.presenters.SplashActivityPresenter;
import com.youzi.youziwallpaper.app.mvp.presenters.VideoDetailActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zzw on 2019/1/11
 */
@Module
public class ActivityModule {

    @Provides
    public static MainActivityContract.Presenter provideMainActivityPresenter(MainActivityPresenter presenterImpl) {
        return presenterImpl;
    }


    @Provides
    public static LoginActivityContract.Presenter provideLoginPresenter(LoginActivityPresenter presenterImpl) {
        return presenterImpl;
    }

    @Provides
    public static SplashActivityContract.Presenter provideSplashActivityPresenter(SplashActivityPresenter presenterImpl) {
        return presenterImpl;
    }

    @Provides
    public static MessageActivityContract.Presenter provideMessageActivityPresenter(MessageActivityPresenter presenterImpl) {
        return presenterImpl;
    }

    @Provides
    public static EditPersonInfoActivityContract.Presenter provideEditPersonInfoActivityPresenter(EditPersonInfoActivityPresenter presenterImpl) {
        return presenterImpl;
    }


    @Provides
    public static SettingActivityContract.Presenter provideSettingActivityPresenter(SettingActivityPresenter presenterImpl) {
        return presenterImpl;
    }

    @Provides
    public static SortVideoListActivityContract.Presenter provideSortListActivityPresenter(SortVideoListActivityPresenter presenterImpl) {
        return presenterImpl;
    }


    @Provides
    public static VideoDetailActivityContract.Presenter provideVideoDetailActivityPresenter(VideoDetailActivityPresenter presenterImpl) {
        return presenterImpl;
    }

    @Provides
    public static SearchActivityContract.Presenter provideSearchActivityPresenter(SearchActivityPresenter presenterImpl) {
        return presenterImpl;
    }



}
