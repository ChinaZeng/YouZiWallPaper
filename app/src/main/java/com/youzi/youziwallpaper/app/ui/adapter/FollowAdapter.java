package com.youzi.youziwallpaper.app.ui.adapter;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.youziwallpaper.R;

import java.util.ArrayList;

/**
 * Created by zzw on 2019/1/20.
 * 描述:
 */
public class FollowAdapter extends BaseQuickAdapter<ThemeBean, BaseViewHolder> {
    public FollowAdapter() {
        super(R.layout.item_follow, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeBean item) {
        //todo 头像字段缺失  话题字段缺失   喜欢 评论  分享数量
        helper.setText(R.id.tv_name, item.getTheme_upload_personnel())
                .setText(R.id.tv_date, item.getTheme_update_time())
                .setText(R.id.tv_des, item.getTheme_title());
        if (item.getDetail() != null && item.getDetail().getImgList() != null
                && item.getDetail().getImgList().size() > 0) {
            Glide.with(helper.itemView.getContext())
                    .load(item.getDetail().getImgList().get(0).getImg_url())
                    .into(helper.<ImageView>getView(R.id.iv_video));
        }

    }
}
