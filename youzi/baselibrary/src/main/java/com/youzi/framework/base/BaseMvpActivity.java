package com.youzi.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.youzi.framework.base.mvp.IBasePresenter;

import javax.inject.Inject;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public abstract class BaseMvpActivity<P extends IBasePresenter> extends BaseActivity {
    @Inject
    protected P mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.bindView(this);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.init();
        }
    }
}
