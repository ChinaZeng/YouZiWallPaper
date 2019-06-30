package com.youzi.youziwallpaper.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youzi.framework.base.BaseMvpRefreshFragment;
import com.youzi.service.api.resp.SortBean;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.SortFragmentContract;
import com.youzi.youziwallpaper.app.ui.activities.SortVideoListActivity;
import com.youzi.youziwallpaper.app.ui.adapter.SortAdapter;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SortFragment extends
        BaseMvpRefreshFragment<SortFragmentContract.Presenter>
        implements SortFragmentContract.View {
    @BindView(R.id.recy)
    RecyclerView recy;

    private SortAdapter sortAdapter;


    public static SortFragment newInstance() {
        return new SortFragment();
    }


    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    protected View provideRefreshContent() {
        return inflate(R.layout.fragment_sort);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
        sortAdapter = new SortAdapter();
        sortAdapter.setOnItemClickListener((adapter, view1, position) -> {
            SortBean bean= sortAdapter.getData().get(position);
            SortVideoListActivity.open(getContext(),bean.label);
        });
        recy.setAdapter(sortAdapter);

        sortAdapter.setEnableLoadMore(true);
        provideRefreshLayout().startRefresh();
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getData();
    }


    @Override
    public void showList(List<SortBean> list) {
        sortAdapter.replaceData(list);
        provideRefreshLayout().refreshCompleted();

    }

    @Override
    public void showError() {
        provideRefreshLayout().refreshCompleted();
    }

}