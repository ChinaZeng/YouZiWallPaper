package com.youzi.framework.common.ui.refresh;

import android.support.annotation.Nullable;

/**
 * 刷新组件的基本功能
 * Created by LuoHaifeng on 2017/3/9.
 */

public interface IRefreshLayout {
    /**
     * 开始刷新
     */
    void startRefresh();

    /**
     * 设置是否开启刷新功能
     * @param enable 开关
     */
    void setRefreshEnable(boolean enable);

    /**
     * 设置是否开启加载更多功能
     * @param enable 开关
     */
    void setLoadMoreEnable(boolean enable);

    /**
     * 刷新完成
     */
    void refreshCompleted();

    /**
     * 刷新失败
     */
    void refreshFailed();

    /**
     * 加载更多完成
     */
    void loadMoreCompleted();

    /**
     * 加载更多失败
     */
    void loadMoreFailed();

    /**
     * 加载结束
     */
    void loadedAll();

    /**
     * 设置加载监听器(回调刷新和加载更多)
     * @param listener 监听器
     */
    void setRefreshLoadMoreListener(@Nullable OnRefreshLoadMoreListener listener);

    boolean isRefreshEnable();

    boolean isLoadMoreEnable();

    interface OnRefreshLoadMoreListener{
        void onRefresh();
        void onLoadMore();
    }
}
