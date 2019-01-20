package com.youzi.youziwallpaper.app.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youzi.youziwallpaper.R;

import java.util.ArrayList;

/**
 * Created by zzw on 2019/1/20.
 * 描述:
 */
public class FollowAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public FollowAdapter() {
        super(R.layout.item_follow, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
