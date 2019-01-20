package com.youzi.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.youzi.framework.base.mvp.IBasePresenter;

import javax.inject.Inject;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public abstract class BaseMvpFragment<P extends IBasePresenter> extends BaseFragment {
    @Inject
    protected P mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mPresenter != null) {
            mPresenter.bindView(this);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.init();
        }
    }
}
