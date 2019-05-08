package com.youzi.framework.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youzi.framework.base.adapter.AdapterToRefreshLayoutWrapper;
import com.youzi.framework.base.mvp.IBasePagingView;
import com.youzi.framework.base.mvp.IBasePresenter;
import com.youzi.framework.common.ui.refresh.IRefreshLayout;

/**
 * Created by LuoHaifeng on 2018/4/25 0025.
 * Email:496349136@qq.com
 */
public abstract class BaseMvpRefreshFragment<P extends IBasePresenter> extends BaseMvpFragment<P> implements IRefreshLayout.OnRefreshLoadMoreListener {
    SwipeRefreshLayout swipeRefreshLayout;
    IRefreshLayout refreshLayout;

    @Override
    protected View provideLayoutView() {
        View view = getLayoutInflater().inflate(R.layout.base_activity_refresh, null);
        swipeRefreshLayout = view.findViewById(R.id.base_refresh_layout);
        View content = provideRefreshContent();
        if (content != null) {
            swipeRefreshLayout.addView(content);
        }
        refreshLayout = new AdapterToRefreshLayoutWrapper(swipeRefreshLayout);
        refreshLayout.setRefreshLoadMoreListener(this);
        return view;
    }

    protected abstract View provideRefreshContent();

    @Override
    public IRefreshLayout provideRefreshLayout() {
        return refreshLayout;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
