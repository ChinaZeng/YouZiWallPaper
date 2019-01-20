package com.youzi.youziwallpaper.di;


import com.youzi.youziwallpaper.app.mvp.contracts.AdFragmentContract;
import com.youzi.youziwallpaper.app.mvp.contracts.FindFragmentContract;
import com.youzi.youziwallpaper.app.mvp.contracts.FollowFragmentContract;
import com.youzi.youziwallpaper.app.mvp.contracts.SortFragmentContract;
import com.youzi.youziwallpaper.app.mvp.contracts.VideoListFragmentContract;
import com.youzi.youziwallpaper.app.mvp.presenters.AdFragmentPresenter;
import com.youzi.youziwallpaper.app.mvp.presenters.FindFragmentPresenter;
import com.youzi.youziwallpaper.app.mvp.presenters.FollowFragmentPresenter;
import com.youzi.youziwallpaper.app.mvp.presenters.SortFragmentPresenter;
import com.youzi.youziwallpaper.app.mvp.presenters.VideoListFragmentPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zzw on 2019/1/11
 */
@Module
public class FragmentModule {

    @Provides
    public static AdFragmentContract.Presenter provideAdFragmentPresenter(AdFragmentPresenter presenterImpl) {
        return presenterImpl;
    }


    @Provides
    public static FindFragmentContract.Presenter provideFindFragmentPresenter(FindFragmentPresenter presenterImpl) {
        return presenterImpl;
    }


    @Provides
    public static VideoListFragmentContract.Presenter provideVideoListFragmentPresenter(VideoListFragmentPresenter presenterImpl) {
        return presenterImpl;
    }


    @Provides
    public static SortFragmentContract.Presenter provideSortFragmentPresenter(SortFragmentPresenter presenterImpl) {
        return presenterImpl;
    }


    @Provides
    public static FollowFragmentContract.Presenter provideFollowFragmentPresenter(FollowFragmentPresenter presenterImpl) {
        return presenterImpl;
    }

}
