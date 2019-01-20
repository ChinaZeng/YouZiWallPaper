package com.youzi.framework.common.compat.rx2;


import io.reactivex.Observable;

/**
 * Created by LuoHaifeng on 2018/2/9 0009.
 * Email:496349136@qq.com
 */

public interface IObservableProvider<T> {
    Observable<T> newObservable();
}
