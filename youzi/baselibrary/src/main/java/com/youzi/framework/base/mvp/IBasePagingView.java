package com.youzi.framework.base.mvp;

import com.youzi.framework.common.util.paging.v2.Paging;

/**
 * Created by LuoHaifeng on 2018/2/2 0002.
 * Email:496349136@qq.com
 */

public interface IBasePagingView extends IBaseView {
    <T> Paging<T> providePagingV2();
}
