package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.youziwallpaper.app.mvp.contracts.MessageActivityContract;

import javax.inject.Inject;

public class MessageActivityPresenter extends BasePresenter<MessageActivityContract.View> implements MessageActivityContract.Presenter {
    @Inject
    public MessageActivityPresenter() {
    }
}
