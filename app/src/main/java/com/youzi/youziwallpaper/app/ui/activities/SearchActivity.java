package com.youzi.youziwallpaper.app.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youzi.framework.base.BaseMvpRefreshActivity;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.SearchActivityContract;
import com.youzi.youziwallpaper.app.ui.adapter.ItemDecoration.LinearLayoutItemDecoration;
import com.youzi.youziwallpaper.app.ui.adapter.SortVideoListAdapter;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zzw on 2019/6/30.
 * 描述:
 */
public class SearchActivity extends BaseMvpRefreshActivity<SearchActivityContract.Presenter>
        implements SearchActivityContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.recy)
    RecyclerView recy;
    @BindView(R.id.et_search)
    EditText editText;


    private SortVideoListAdapter adapter;
    private int nowPage = 1;

    private String mSearchKey;

    public static void open(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected View provideRefreshContent() {
        return inflate(R.layout.activity_search);
    }

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        recy.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SortVideoListAdapter(false);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoDetailActivity.open(SearchActivity.this,
                        (ThemeBean) adapter.getData().get(position));
            }
        });
        recy.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(this, recy);
        recy.addItemDecoration(new LinearLayoutItemDecoration((int)
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        1.0f, getResources().getDisplayMetrics()
                )
                , 0Xff414141
        ));

    }


    @Override
    public void showList(List<ThemeBean> list, int page) {
        if (page == 1) {
            adapter.replaceData(list);
        } else {
            adapter.getData().addAll(list);
            adapter.notifyDataSetChanged();
        }

        if (list.isEmpty()) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }

        provideRefreshLayout().refreshCompleted();

    }

    @Override
    public void showError() {
        provideRefreshLayout().refreshCompleted();
        adapter.loadMoreComplete();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        nowPage = 1;
        mPresenter.getData(mSearchKey, nowPage);
    }

    @Override
    public void onLoadMoreRequested() {
        nowPage++;
        mPresenter.getData(mSearchKey, nowPage);
    }


    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        mSearchKey = editText.getText().toString().trim();
        provideRefreshLayout().startRefresh();
    }
}
