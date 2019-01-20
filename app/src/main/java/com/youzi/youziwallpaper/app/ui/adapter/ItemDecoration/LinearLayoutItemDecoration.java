package com.youzi.youziwallpaper.app.ui.adapter.ItemDecoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by zzw on 2017/5/25.
 * Version:
 * Des:
 */

public class LinearLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private int dimen;
    private int color;
    //是否是竖直排列
    private boolean isVer;

    public LinearLayoutItemDecoration(int dimen, int color) {
        this(dimen, color, true);
    }

    public LinearLayoutItemDecoration(int dimen, int color, boolean isVer) {
        this.dimen = dimen;
        this.color = color;
        this.isVer = isVer;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildAdapterPosition(view);//当前item的位置  view当前item
        //留出分割线的位置
        //如果不是第0个位置  就在每个item上面留出mDriver的高度 然后在onDraw里面画出mDriver
        if (pos != 0) {
            if (isVer) {
                outRect.top = dimen;
            } else {
                outRect.left = dimen;
            }

        }
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //得到item数量
        int childCount = parent.getChildCount();

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);


        Rect rect = new Rect();

        if (isVer) {
            rect.left = parent.getPaddingLeft();
            rect.right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 1; i < childCount; i++) {//从下标为1的位置开始画
                rect.bottom = parent.getChildAt(i).getTop();
                rect.top = rect.bottom - dimen;
                c.drawRect(rect, paint);
            }
        } else {
            rect.top = parent.getPaddingTop();
            rect.bottom = parent.getPaddingBottom();

            for (int i = 1; i < childCount; i++) {//从下标为1的位置开始画
                rect.right = parent.getChildAt(i).getLeft();
                rect.left = rect.right - dimen;
                c.drawRect(rect, paint);
            }
        }

    }
}
