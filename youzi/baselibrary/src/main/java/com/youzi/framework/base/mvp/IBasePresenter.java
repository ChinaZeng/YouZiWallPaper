package com.youzi.framework.base.mvp;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public interface IBasePresenter<V extends IBaseView>{
    void bindView(V view);

    void init();

    void refreshContent();
}
