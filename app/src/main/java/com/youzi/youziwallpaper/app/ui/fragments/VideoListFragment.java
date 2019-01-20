package com.youzi.youziwallpaper.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.youzi.framework.base.BaseMvpRefreshFragment;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.VideoListFragmentContract;
import com.youzi.youziwallpaper.app.ui.adapter.ItemDecoration.GridItemDecoration;
import com.youzi.youziwallpaper.app.ui.adapter.VideoListAdapter;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoListFragment extends BaseMvpRefreshFragment<VideoListFragmentContract.Presenter> implements VideoListFragmentContract.View {
    @BindView(R.id.recy)
    RecyclerView recy;


    public static VideoListFragment newInstance() {
        return new VideoListFragment();
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
        recy.setAdapter(adapter);

        int g = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , 6, getResources().getDisplayMetrics());
        recy.addItemDecoration(new GridItemDecoration(g,g));

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