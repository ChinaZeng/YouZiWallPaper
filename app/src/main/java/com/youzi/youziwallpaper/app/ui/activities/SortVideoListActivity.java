package com.youzi.youziwallpaper.app.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youzi.framework.base.BaseMvpRefreshActivity;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.SortVideoListActivityContract;
import com.youzi.youziwallpaper.app.ui.adapter.ItemDecoration.LinearLayoutItemDecoration;
import com.youzi.youziwallpaper.app.ui.adapter.SortVideoListAdapter;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SortVideoListActivity extends BaseMvpRefreshActivity<SortVideoListActivityContract.Presenter> implements SortVideoListActivityContract.View {
    @BindView(R.id.recy)
    RecyclerView recy;

    private SortVideoListAdapter adapter;

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

        //todo 获取名称设置
        setTitle("分类名称");

        recy.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SortVideoListAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                VideoDetailActivity.open(SortVideoListActivity.this);
            }
        });
        recy.setAdapter(adapter);
        recy.addItemDecoration(new LinearLayoutItemDecoration((int)
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        1.0f, getResources().getDisplayMetrics()
                )
                , 0Xff414141
        ));

        testData();
    }

    private void testData() {
        List<String> test = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            test.add("nihao" + i);
        }
        adapter.replaceData(test);
    }
}