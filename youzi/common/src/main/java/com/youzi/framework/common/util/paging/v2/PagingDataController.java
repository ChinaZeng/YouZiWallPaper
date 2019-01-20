package com.youzi.framework.common.util.paging.v2;

import io.reactivex.Observable;

/**
 * Created by LuoHaifeng on 2018/5/21 0021.
 * Email:496349136@qq.com
 */
public interface PagingDataController<T> {
    Observable<T> provideDataSource(int pageIndex, int pageSize);

    boolean haveData(Paging<T> paging);

    boolean computeHaveMoreData(Paging<T> paging, T lastPagingResult);

    boolean isValidPage(Paging<T> paging, T lastPagingResult);

    boolean haveMoreData(Paging<T> paging);
}
