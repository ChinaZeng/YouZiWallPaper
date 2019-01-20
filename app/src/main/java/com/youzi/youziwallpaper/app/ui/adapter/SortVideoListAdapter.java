package com.youzi.youziwallpaper.app.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youzi.youziwallpaper.R;

import java.util.ArrayList;

/**
 * Created by zzw on 2019/1/20.
 * 描述:
 */
public class SortVideoListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SortVideoListAdapter() {
        super(R.layout.item_sort_list, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }

}
