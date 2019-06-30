package com.youzi.youziwallpaper.app.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.image.ImageLoader;

import java.util.ArrayList;

/**
 * Created by zzw on 2019/1/20.
 * 描述:
 */
public class SortVideoListAdapter extends BaseQuickAdapter<ThemeBean, BaseViewHolder> {

    private boolean isShowNum;

    public SortVideoListAdapter() {
        this(true);
    }

    public SortVideoListAdapter(boolean isShowNum) {
        super(R.layout.item_sort_list, new ArrayList<>());
        this.isShowNum = isShowNum;
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeBean item) {

        helper
                .setVisible(R.id.tv_num, isShowNum)
                .setText(R.id.tv_num, (mData.indexOf(item) + 1) + "")
                .setText(R.id.tv_name, item.getTheme_upload_personnel())
                .setText(R.id.tv_des, item.getTheme_title());


        ThemeBean.DetailBean detailBean = item.getDetail();

        if (detailBean == null) {
            return;
        }

        helper.setText(R.id.iv_num, detailBean.getThemeDownloadNumber())
                .setText(R.id.tv_zan, "已有" + detailBean.getCollectNum() + "人关注");


        if (detailBean.getImgList() != null
                && detailBean.getImgList().size() > 0) {

            ImageLoader.load(detailBean.getImgList().get(0).getImg_url(),
                    R.mipmap.img_radius_rectangle, helper.getView(R.id.iv_logo));

        }


    }

}
