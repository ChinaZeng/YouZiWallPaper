package com.youzi.youziwallpaper.image;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by zzw on 2019/5/25.
 * 描述:
 */
public class ImageLoader {


    public static void load(String path, int placeholder, ImageView iv) {
        if (iv == null) return;
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)//图片加载出来前，显示的图片
                .fallback(placeholder) //url为空的时候,显示的图片
                .error(placeholder);//图片加载失败后，显示的图片

        Glide.with(iv.getContext())
                .load(path) //图片地址
                .apply(options)
                .into(iv);


    }
}
