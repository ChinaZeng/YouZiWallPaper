package com.youzi.framework.common.util.paging.v2;

/**
 * Created by LuoHaifeng on 2018/5/21 0021.
 * Email:496349136@qq.com
 */
public interface PagingLauncher{
    enum UseLaunchMode {
        MODE_LOADING,
        MODE_REFRESH,
        MODE_NONE
    }

    /**
     * Refresh模式优先
     */
    void startRefresh();

    /**
     * Loading模式优先
     */
    void startLoading();

    /**
     * 仅使用Loading模式
     */
    void startLoadingOnly();

    /**
     * 仅使用Refresh模式
     */
    void startRefreshOnly();

    /**
     * 直接发起请求
     */
    void startOnlyRequest();
}
