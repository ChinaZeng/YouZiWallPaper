package com.youzi.youziwallpaper.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youzi.framework.base.BaseMvpRefreshFragment;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.RecommendFragmentContract;
import com.youzi.youziwallpaper.app.ui.activities.VideoDetailActivity;
import com.youzi.youziwallpaper.app.ui.adapter.ItemDecoration.GridItemDecoration;
import com.youzi.youziwallpaper.app.ui.adapter.VideoListAdapter;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import java.util.List;

import butterknife.BindView;

public class RecommendFragment extends BaseMvpRefreshFragment<RecommendFragmentContract.Presenter> implements RecommendFragmentContract.View {
    @BindView(R.id.recy)
    RecyclerView recy;


    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }


    private VideoListAdapter adapter;

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected View provideRefreshContent() {
        return inflate(R.layout.fragment_video_list);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recy.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new VideoListAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ThemeBean bean = (ThemeBean) adapter.getData().get(position);
                VideoDetailActivity.open(getContext(),bean);
            }
        });
        recy.setAdapter(adapter);

        int g = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , 6, getResources().getDisplayMetrics());
        recy.addItemDecoration(new GridItemDecoration(g, g));

        provideRefreshLayout().startRefresh();
    }


    @Override
    public void showList(List<ThemeBean> list) {
        adapter.replaceData(list);
        provideRefreshLayout().refreshCompleted();
    }

    @Override
    public void showError() {
        provideRefreshLayout().refreshCompleted();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getData();
    }
}