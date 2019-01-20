package com.youzi.youziwallpaper.app.ui.adapter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.ui.activities.VideoDetailActivity;
import com.youzi.youziwallpaper.app.ui.adapter.ItemDecoration.LinearLayoutItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzw on 2019/1/20.
 * 描述:
 */
public class SortAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SortAdapter() {
        super(R.layout.item_sort, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RecyclerView recyclerView = helper.getView(R.id.sort_recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false
        ));
        SortImgAdapter adapter;
        if (recyclerView.getAdapter() != null &&
                recyclerView.getAdapter() instanceof SortImgAdapter) {
            adapter = (SortImgAdapter) recyclerView.getAdapter();
        } else {
            adapter = new SortImgAdapter();
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    VideoDetailActivity.open(mContext);
                }
            });
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new LinearLayoutItemDecoration(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                            , 1.0f, mContext.getResources().getDisplayMetrics()
                    ), 0xff000000, false
            ));
            recyclerView.setTag(adapter);
        }
        adapter.replaceData(testData());
    }

    private List<String> testData() {
        List<String> test = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            test.add("nihao" + i);
        }
        return test;
    }


    private class SortImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        SortImgAdapter() {
            super(R.layout.item_sort_img, new ArrayList<>());
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setImageResource(R.id.iv_img, R.mipmap.ic_launcher);
        }
    }
}
