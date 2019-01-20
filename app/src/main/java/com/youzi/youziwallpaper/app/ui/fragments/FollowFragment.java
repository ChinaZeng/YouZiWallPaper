package com.youzi.youziwallpaper.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youzi.framework.base.BaseMvpFragment;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.FollowFragmentContract;
import com.youzi.youziwallpaper.app.ui.activities.VideoDetailActivity;
import com.youzi.youziwallpaper.app.ui.adapter.FollowAdapter;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FollowFragment extends BaseMvpFragment<FollowFragmentContract.Presenter> implements FollowFragmentContract.View {
    @BindView(R.id.recy)
    RecyclerView recy;
    private FollowAdapter adapter;


    public static FollowFragment newInstance() {
        return new FollowFragment();
    }

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected View provideLayoutView() {
        return inflate(R.layout.fragment_follow);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoDetailActivity.open(getContext());
            }
        });
        adapter = new FollowAdapter();
        recy.setAdapter(adapter);

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