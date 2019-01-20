package com.youzi.framework.base.adapter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youzi.framework.common.ui.refresh.IRefreshLayout;

/**
 * Created by LuoHaifeng on 2018/2/5 0005.
 * Email:496349136@qq.com
 */

public class AdapterToRefreshLayoutWrapper implements IRefreshLayout {
    private BaseQuickAdapter quickAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private OnRefreshLoadMoreListener onRefreshLoadMoreListener;

    public AdapterToRefreshLayoutWrapper(BaseQuickAdapter quickAdapter, RecyclerView recyclerView, SwipeRefreshLayout refreshLayout) {
        this.quickAdapter = quickAdapter;
        this.recyclerView = recyclerView;
        this.refreshLayout = refreshLayout;
    }

    public AdapterToRefreshLayoutWrapper(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    @Override
    public void startRefresh() {
        if (this.refreshLayout != null) {
            this.refreshLayout.setRefreshing(true);
        }
        if (onRefreshLoadMoreListener != null) {
            this.onRefreshLoadMoreListener.onRefresh();
        }
    }

    @Override
    public void setRefreshEnable(boolean enable) {
        if (this.refreshLayout != null) {
            this.refreshLayout.setEnabled(enable);
        }
    }

    @Override
    public void setLoadMoreEnable(boolean enable) {
        if (quickAdapter != null) {
            this.quickAdapter.setEnableLoadMore(enable);
        }
    }

    @Override
    public void refreshCompleted() {
        if (this.refreshLayout != null) {
            this.refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void refreshFailed() {
        if (this.refreshLayout != null) {
            this.refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void loadMoreCompleted() {
        if (quickAdapter != null) {
            this.quickAdapter.loadMoreComplete();
        }
    }

    @Override
    public void loadMoreFailed() {
        if (quickAdapter != null) {
            this.quickAdapter.loadMoreFail();
        }
    }

    @Override
    public void loadedAll() {
        if (quickAdapter != null) {
            this.quickAdapter.loadMoreEnd(false);
        }
    }

    @Override
    public void setRefreshLoadMoreListener(final OnRefreshLoadMoreListener listener) {
        this.onRefreshLoadMoreListener = listener;
        if (this.refreshLayout != null) {
            this.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (listener != null) {
                        listener.onRefresh();
                    }
                }
            });
        }

        if (quickAdapter != null) {
            this.quickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (listener != null) {
                        listener.onLoadMore();
                    }
                }
            }, recyclerView);
        }
    }

    @Override
    public boolean isRefreshEnable() {
        if (this.refreshLayout != null) {
            return this.refreshLayout.isEnabled();
        }

        return false;
    }

    @Override
    public boolean isLoadMoreEnable() {
        if (quickAdapter != null) {
            return this.quickAdapter.isLoadMoreEnable();
        }
        return false;
    }
}
