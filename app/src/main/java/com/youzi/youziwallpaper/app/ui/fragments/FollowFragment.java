package com.youzi.youziwallpaper.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youzi.framework.base.BaseMvpFragment;
import com.youzi.framework.base.BaseMvpRefreshFragment;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.FollowFragmentContract;
import com.youzi.youziwallpaper.app.ui.activities.VideoDetailActivity;
import com.youzi.youziwallpaper.app.ui.adapter.FollowAdapter;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

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
                VideoDetailActivity.open(getContext());
            }
        });
        recy.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(this, recy);

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
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getData(page);
    }
}