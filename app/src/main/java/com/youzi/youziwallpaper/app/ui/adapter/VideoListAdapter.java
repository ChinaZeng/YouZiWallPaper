package com.youzi.youziwallpaper.app.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youzi.youziwallpaper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzw on 2019/1/12.
 * 描述:
 */
public class VideoListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int width;

    public VideoListAdapter() {
        super(R.layout.item_video_list, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        View view = helper.getView(R.id.iv_video);
        if (width == 0) {
            view.post(() -> {
                width = view.getWidth();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = width;
            });
        } else {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = width;
        }
    }
}
