package com.youzi.framework.common.util.paging.rx2.callback;

import com.youzi.framework.common.ui.refresh.IRefreshLayout;

/**
 * 功能描述:上下拉内容提供接口
 * Created by LuoHaifeng on 2017/4/21.
 * Email:496349136@qq.com
 */

public interface IRefreshProvider extends ILoadingProvider {
    /**
     * 提供刷新布局
     */
    IRefreshLayout provideRefreshLayout();
}
