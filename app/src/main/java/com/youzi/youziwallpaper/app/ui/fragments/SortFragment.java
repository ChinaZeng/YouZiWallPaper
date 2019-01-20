package com.youzi.youziwallpaper.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youzi.framework.base.BaseMvpRefreshFragment;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.SortFragmentContract;
import com.youzi.youziwallpaper.app.ui.activities.SortVideoListActivity;
import com.youzi.youziwallpaper.app.ui.adapter.SortAdapter;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SortFragment extends BaseMvpRefreshFragment<SortFragmentContract.Presenter> implements SortFragmentContract.View {
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
        sortAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(SortVideoListActivity.class);
            }
        });
        recy.setAdapter(sortAdapter);

        testData();
    }

    private void testData() {
        List<String> test = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            test.add("nihao" + i);
        }
        sortAdapter.replaceData(test);
    }
}