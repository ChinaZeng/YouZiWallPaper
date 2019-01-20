package com.youzi.framework.base.mvp;

/**
 * Created by LuoHaifeng on 2018/1/31 0031.
 * Email:496349136@qq.com
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    protected V mView;

    public void bindView(V view) {
        this.mView = view;
    }

    @Override
    public void init() {

    }

    @Override
    public void refreshContent() {

    }
}
