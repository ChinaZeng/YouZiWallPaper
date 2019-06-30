package com.youzi.youziwallpaper.app.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youzi.framework.base.BaseMvpRefreshActivity;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.SortVideoListActivityContract;
import com.youzi.youziwallpaper.app.ui.adapter.ItemDecoration.LinearLayoutItemDecoration;
import com.youzi.youziwallpaper.app.ui.adapter.SortVideoListAdapter;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import java.util.List;

import butterknife.BindView;

public class SortVideoListActivity extends BaseMvpRefreshActivity<SortVideoListActivityContract.Presenter> implements SortVideoListActivityContract.View {
    @BindView(R.id.recy)
    RecyclerView recy;

    private SortVideoListAdapter adapter;

    private String mSortName;
    private static final String NAME_KEY = "NAME_KEY";


    public static void open(Context context, String bean) {
        Intent intent = new Intent(context, SortVideoListActivity.class);
        intent.putExtra(NAME_KEY, bean);
        context.startActivity(intent);
    }

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }


    @Override
    protected View provideRefreshContent() {
        return inflate(R.layout.activity_sort_list);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mSortName = getIntent().getStringExtra(NAME_KEY);

        setTitle(mSortName);

        recy.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SortVideoListAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoDetailActivity.open(SortVideoListActivity.this,
                        (ThemeBean) adapter.getData().get(position));
            }
        });
        recy.setAdapter(adapter);
        recy.addItemDecoration(new LinearLayoutItemDecoration((int)
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        1.0f, getResources().getDisplayMetrics()
                )
                , 0Xff414141
        ));


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
        mPresenter.getData(mSortName);
    }
}