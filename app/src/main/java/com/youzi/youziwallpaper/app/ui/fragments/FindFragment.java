package com.youzi.youziwallpaper.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.youzi.framework.base.BaseMvpFragment;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.FindFragmentContract;
import com.youzi.youziwallpaper.app.ui.activities.LoginActivity;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
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
        mFragments.add(VideoListFragment.newInstance());
        mFragments.add(FollowFragment.newInstance());

        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
    }

    @OnClick({R.id.iv_header, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
                startActivity(LoginActivity.class);
                break;
            case R.id.iv_search:
                break;
        }
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
}