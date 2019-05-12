package com.youzi.youziwallpaper.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youzi.framework.base.BaseMvpFragment;
import com.youzi.framework.base.BaseMvpRefreshFragment;
import com.youzi.framework.common.util.login.event.LoginEvent;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.FollowFragmentContract;
import com.youzi.youziwallpaper.app.ui.activities.VideoDetailActivity;
import com.youzi.youziwallpaper.app.ui.adapter.FollowAdapter;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FollowFragment extends
        BaseMvpRefreshFragment<FollowFragmentContract.Presenter>
        implements FollowFragmentContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.recy)
    RecyclerView recy;
    private FollowAdapter adapter;
    int page = 1;

    public static FollowFragment newInstance() {
        return new FollowFragment();
    }

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }


    @Override
    protected View provideRefreshContent() {
        return inflate(R.layout.fragment_follow);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FollowAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ThemeBean bean = (ThemeBean) adapter.getData().get(position);
                VideoDetailActivity.open(getContext(),bean);
            }
        });
        recy.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(this, recy);
        provideRefreshLayout().startRefresh();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login(LoginEvent event) {
        provideRefreshLayout().startRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        page = 1;
        mPresenter.getData(page);
    }


    @Override
    public void showList(int total,int page, List<ThemeBean> list) {
        if (page == 1) {
            adapter.replaceData(list);
        } else {
            adapter.addData(list);
        }

        provideRefreshLayout().refreshCompleted();
        adapter.loadMoreComplete();
        if(total<=adapter.getData().size()){
            adapter.loadMoreEnd(true);
        }

    }

    @Override
    public void loadError(int page) {
        provideRefreshLayout().refreshCompleted();
        adapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getData(page);
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