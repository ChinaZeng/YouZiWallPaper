package com.youzi.framework.common.util.lifecycle.rx1;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;

import javax.annotation.Nonnull;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public abstract class LifeCycleProviderProxy<T> implements LifecycleProvider<T> {
    protected BehaviorSubject<T> subject;

    public LifeCycleProviderProxy(BehaviorSubject<T> subject) {
        this.subject = subject;
    }

    @Nonnull
    @Override
    public Observable<T> lifecycle() {
        return subject.asObservable();
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