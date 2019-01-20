package com.youzi.framework.base.mvp;

import java.util.List;

/**
 * Created by LuoHaifeng on 2018/2/5 0005.
 * Email:496349136@qq.com
 */

public interface IBasePagingListView<T> extends IBasePagingView {
    void addListData(List<T> data, boolean clearOld);
}
