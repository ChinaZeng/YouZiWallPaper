package com.youzi.youziwallpaper.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.youzi.framework.base.BaseMvpFragment;
import com.youzi.framework.common.util.login.LoginManager;
import com.youzi.framework.common.util.login.event.LoginEvent;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.bean.UserInfoBean;
import com.youzi.youziwallpaper.app.mvp.contracts.FindFragmentContract;
import com.youzi.youziwallpaper.app.ui.activities.LoginActivity;
import com.youzi.youziwallpaper.di.DaggerAppComponent;
import com.youzi.youziwallpaper.image.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class FindFragment extends BaseMvpFragment<FindFragmentContract.Presenter> implements FindFragmentContract.View {
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;


    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "推荐", "关注"
    };

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected View provideLayoutView() {
        return inflate(R.layout.fragment_find);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragments.add(RecommendFragment.newInstance());
        mFragments.add(FollowFragment.newInstance());

        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);

        showInfo();

    }

    @OnClick({R.id.iv_header, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
                LoginActivity.open(getContext(), getClass().getName());
                break;
            case R.id.iv_search:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login(LoginEvent event) {
        showInfo();
    }

    void showInfo() {

        if (!LoginManager.getInstance().isLogin()) {
            return;
        }

        UserInfoBean loginResult = LoginManager.getInstance().getLastLoginResult();
        if (loginResult != null)
            ImageLoader.load(loginResult.headerUrl,
                    R.mipmap.ic_header_def, ivHeader);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}