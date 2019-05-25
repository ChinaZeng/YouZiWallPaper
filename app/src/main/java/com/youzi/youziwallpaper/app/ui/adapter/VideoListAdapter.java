package com.youzi.youziwallpaper.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.image.ImageLoader;

import java.util.ArrayList;


/**
 * Created by zzw on 2019/1/12.
 * 描述:
 */
public class VideoListAdapter extends BaseQuickAdapter<ThemeBean, BaseViewHolder> {
    private int width;

    public VideoListAdapter() {
        super(R.layout.item_video_list, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeBean item) {
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

        helper.setText(R.id.tv_nick_name, item.getTheme_upload_personnel())
                .setText(R.id.tv_video_des, item.getTheme_title());
        ThemeBean.DetailBean detailBean = item.getDetail();
        if (detailBean != null && detailBean.getImgList() != null
                && detailBean.getImgList().size() > 0) {
            ImageLoader.load(detailBean.getImgList().get(0).getImg_url(),
                    R.mipmap.img_radius_rectangle, helper.getView(R.id.iv_video));
        }
        // TODO: 2019/5/7 头像字段  话题字段缺失


    }
}
