package com.youzi.framework.common.util.lifecycle.rx2;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public abstract class LifeCycleProviderProxy<T> implements LifecycleProvider<T> {
    protected BehaviorSubject<T> subject;

    public LifeCycleProviderProxy(BehaviorSubject<T> subject) {
        this.subject = subject;
    }

    @Nonnull
    @Override
    public Observable<T> lifecycle() {
        return subject.hide();
    }

    @Nonnull
    @Override
    public <T1> LifecycleTransformer<T1> bindUntilEvent(@Nonnull T event) {
        return RxLifecycle.bindUntilEvent(subject, event);
    }

    public BehaviorSubject<T> getSubject() {
        return subject;
    }
}