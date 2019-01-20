package com.youzi.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youzi.framework.base.adapter.AdapterToRefreshLayoutWrapper;
import com.youzi.framework.base.mvp.IBasePagingListView;
import com.youzi.framework.base.mvp.IBasePresenter;
import com.youzi.framework.common.ui.refresh.IRefreshLayout;

import java.util.List;

/**
 * Created by LuoHaifeng on 2018/2/5 0005.
 * Email:496349136@qq.com
 */

public abstract class BaseMvpListFragment<T, P extends IBasePresenter> extends BaseMvpFragment<P> implements IBasePagingListView<T> {
    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;

    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    private IRefreshLayout refreshLayoutInterface;

    @Override
    protected View provideLayoutView() {
        return getLayoutInflater().inflate(R.layout.base_activity_simple_list, null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if(view != null) {
            recyclerView = view.findViewById(provideRecyclerViewId());
            swipeRefreshLayout = view.findViewById(provideRefreshLayoutId());
            mAdapter = onCreateAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(mAdapter);
            refreshLayoutInterface = new AdapterToRefreshLayoutWrapper(mAdapter, recyclerView, swipeRefreshLayout);
        }
        return view;
    }


    protected int provideRecyclerViewId(){
        return R.id.base_rv_list;
    }

    protected int provideRefreshLayoutId(){
        return R.id.base_refresh_layout;
    }

    protected BaseQuickAdapter<T, BaseViewHolder> onCreateAdapter() {
        return new BaseQuickAdapter<T, BaseViewHolder>(provideItemLayout()) {
            @Override
            protected void convert(BaseViewHolder helper, T item) {
                onBindItem(helper, item);
            }
        };
    }

    @Override
    public void addListData(List<T> data, boolean clearOld) {
        if (clearOld) {
            mAdapter.setNewData(data);
        } else {
            mAdapter.addData(data);
        }
    }

    @Override
    public IRefreshLayout provideRefreshLayout() {
        return refreshLayoutInterface;
    }

    protected abstract int provideItemLayout();

    protected abstract void onBindItem(BaseViewHolder holder, T item);
}
