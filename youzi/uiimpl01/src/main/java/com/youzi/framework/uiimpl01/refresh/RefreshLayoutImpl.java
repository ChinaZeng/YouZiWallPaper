package com.youzi.framework.uiimpl01.refresh;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.youzi.framework.common.ui.refresh.IRefreshLayout;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public class RefreshLayoutImpl extends SmartRefreshLayout implements IRefreshLayout {
    public RefreshLayoutImpl(Context context) {
        this(context,null);
    }

    public RefreshLayoutImpl(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public RefreshLayoutImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void startRefresh() {
        super.autoRefresh();
    }

    @Override
    public void setRefreshEnable(boolean enable) {
        super.setEnableRefresh(enable);
    }

    @Override
    public void setLoadMoreEnable(boolean enable) {
        super.setEnableLoadmore(enable);
    }

    @Override
    public void refreshCompleted() {
        super.finishRefresh(true);
    }

    @Override
    public void refreshFailed() {
        super.finishRefresh(false);
    }

    @Override
    public void loadMoreCompleted() {
        super.finishLoadmore(true);
    }

    @Override
    public void loadMoreFailed() {
        super.finishLoadmore(false);
    }

    @Override
    public void loadedAll() {
        super.finishLoadmoreWithNoMoreData();
    }

    @Override
    public void setRefreshLoadMoreListener(final OnRefreshLoadMoreListener listener) {
        super.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (listener != null) {
                    listener.onLoadMore();
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (listener != null) {
                    listener.onRefresh();
                }
            }
        });
    }

    @Override
    public boolean isRefreshEnable() {
        return super.isEnableRefresh();
    }

    @Override
    public boolean isLoadMoreEnable() {
        return super.isEnableLoadmore();
    }
}
