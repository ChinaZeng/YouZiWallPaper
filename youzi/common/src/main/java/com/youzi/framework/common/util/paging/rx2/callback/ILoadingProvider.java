package com.youzi.framework.common.util.paging.rx2.callback;
import com.youzi.framework.common.ui.ILoadingHelper;

/**
 * 功能描述:Loading
 * Created by LuoHaifeng on 2017/4/13.
 * Email:496349136@qq.com
 */

public interface ILoadingProvider{

    /***
     * @return 提供加载工具, 方便布局切换
     */
    ILoadingHelper provideLoadingHelper();
}
