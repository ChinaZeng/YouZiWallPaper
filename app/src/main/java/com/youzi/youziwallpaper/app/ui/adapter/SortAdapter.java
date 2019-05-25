package com.youzi.youziwallpaper.app.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youzi.service.api.resp.SortBean;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.ui.activities.VideoDetailActivity;
import com.youzi.youziwallpaper.app.ui.adapter.ItemDecoration.LinearLayoutItemDecoration;
import com.youzi.youziwallpaper.image.ImageLoader;

import java.util.ArrayList;

/**
 * Created by zzw on 2019/1/20.
 * 描述:
 */
public class SortAdapter extends BaseQuickAdapter<SortBean, BaseViewHolder> {
    public SortAdapter() {
        super(R.layout.item_sort, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, SortBean item) {
        // TODO: 2019/5/12  头像 数量 热门名称
        helper.setText(R.id.tv_name_hint, item.label);

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
                    ThemeBean bean = (ThemeBean) adapter.getData().get(position);
                    VideoDetailActivity.open(mContext, bean);
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
        adapter.replaceData(item.list == null ? new ArrayList<>() : item.list);
    }


    private class SortImgAdapter extends BaseQuickAdapter<ThemeBean, BaseViewHolder> {

        SortImgAdapter() {
            super(R.layout.item_sort_img, new ArrayList<>());
        }

        @Override
        protected void convert(BaseViewHolder helper, ThemeBean item) {
            ThemeBean.DetailBean detailBean = item.getDetail();
            if (detailBean != null && detailBean.getImgList() != null
                    && detailBean.getImgList().size() > 0) {
                ImageLoader.load(detailBean.getImgList().get(0).getImg_url()
                        , R.mipmap.img_sort_def, helper.getView(R.id.iv_img));
            }

        }
    }
}
