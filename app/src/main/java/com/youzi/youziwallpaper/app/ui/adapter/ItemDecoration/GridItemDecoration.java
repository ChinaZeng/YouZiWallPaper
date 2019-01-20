package com.youzi.youziwallpaper.app.ui.adapter.ItemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import java.lang.reflect.Type;

/**
 * Created by zzw on 2019/1/12.
 * 描述:
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int right;
    private int bottom;


    public GridItemDecoration(int right, int bottom) {
        this.right = right;
        this.bottom = bottom;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // 四个方向的偏移值
        int nowRight = right;
        int nowBottom = bottom;

        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        if (isLastColumn(position, parent)) {// 是否是最后一列
            nowRight = 0;
        }
        if (isLastRow(position, parent)) {// 是否是最后一行
            nowBottom = 0;
        }
        outRect.set(0, 0, nowRight, nowBottom);
    }

    /**
     * 是否是最后一列
     */
    public boolean isLastColumn(int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        if ((itemPosition + 1) % spanCount == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是最后一行
     */
    public boolean isLastRow(int itemPosition, RecyclerView parent) {

        int spanCount = getSpanCount(parent);

        int childCount = parent.getAdapter().getItemCount();

        int rowNumber = childCount % spanCount == 0 ? childCount / spanCount : (childCount / spanCount) + 1;

        if (itemPosition > ((rowNumber - 1) * spanCount - 1)) {
            return true;
        }

        return false;
    }

    /**
     * 获取一行有多少列
     */
    public int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            // 获取一行的spanCount
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            return spanCount;
        }
        return 1;
    }

}
