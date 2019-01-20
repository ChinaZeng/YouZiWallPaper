package com.youzi.framework.common.util.lifecycle.rx2;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import javax.annotation.Nonnull;

import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by LuoHaifeng on 2017/12/26 0026.
 * Email:496349136@qq.com
 */

public class LifecyleProviderCompat {
    private static Map<Activity, LifeCycleProviderProxy<ActivityEvent>> activityLifecycleProviderWeakHashMap = Collections.synchronizedMap(new WeakHashMap<Activity, LifeCycleProviderProxy<ActivityEvent>>());
    private static Map<Fragment, LifeCycleProviderProxy<FragmentEvent>> frgamentLifecycleProviderWeakHashMap = Collections.synchronizedMap(new WeakHashMap<Fragment, LifeCycleProviderProxy<FragmentEvent>>());
    private static Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            getBehaviorSubject(activity).onNext(ActivityEvent.CREATE);
            if (activity instanceof FragmentActivity) {
                installFragmentSupport(((FragmentActivity) activity).getSupportFragmentManager());
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {
            getBehaviorSubject(activity).onNext(ActivityEvent.START);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            getBehaviorSubject(activity).onNext(ActivityEvent.RESUME);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            getBehaviorSubject(activity).onNext(ActivityEvent.PAUSE);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            getBehaviorSubject(activity).onNext(ActivityEvent.STOP);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            getBehaviorSubject(activity).onNext(ActivityEvent.DESTROY);
        }

        private BehaviorSubject<ActivityEvent> getBehaviorSubject(Activity activity) {
            return LifecyleProviderCompat.getActivityLifcycleProvider(activity).getSubject();
        }
    };
    private static FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
        @Override
        public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
            super.onFragmentAttached(fm, f, context);
            getBehaviorSubject(f).onNext(FragmentEvent.ATTACH);
        }

        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            super.onFragmentCreated(fm, f, savedInstanceState);
            getBehaviorSubject(f).onNext(FragmentEvent.CREATE);
        }

        @Override
        public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState);
            getBehaviorSubject(f).onNext(FragmentEvent.CREATE_VIEW);
        }

        @Override
        public void onFragmentStarted(FragmentManager fm, Fragment f) {
            super.onFragmentStarted(fm, f);
            getBehaviorSubject(f).onNext(FragmentEvent.START);
        }

        @Override
        public void onFragmentResumed(FragmentManager fm, Fragment f) {
            super.onFragmentResumed(fm, f);
            getBehaviorSubject(f).onNext(FragmentEvent.RESUME);
        }

        @Override
        public void onFragmentPaused(FragmentManager fm, Fragment f) {
            super.onFragmentPaused(fm, f);
            getBehaviorSubject(f).onNext(FragmentEvent.PAUSE);
        }

        @Override
        public void onFragmentStopped(FragmentManager fm, Fragment f) {
            super.onFragmentStopped(fm, f);
            getBehaviorSubject(f).onNext(FragmentEvent.STOP);
        }

        @Override
        public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
            super.onFragmentViewDestroyed(fm, f);
            getBehaviorSubject(f).onNext(FragmentEvent.DESTROY_VIEW);
        }

        @Override
        public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
            super.onFragmentDestroyed(fm, f);
            getBehaviorSubject(f).onNext(FragmentEvent.DESTROY);
        }

        @Override
        public void onFragmentDetached(FragmentManager fm, Fragment f) {
            super.onFragmentDetached(fm, f);
            getBehaviorSubject(f).onNext(FragmentEvent.DETACH);
        }

        private BehaviorSubject<FragmentEvent> getBehaviorSubject(Fragment fragment) {
            return LifecyleProviderCompat.getFragmentLifecycleProvider(fragment).getSubject();
        }
    };

    @SuppressWarnings({"unchecked", "SynchronizationOnLocalVariableOrMethodParameter"})
    public static LifeCycleProviderProxy<ActivityEvent> getActivityLifcycleProvider(Activity activity) {
        synchronized (activity) {
            LifeCycleProviderProxy<ActivityEvent> lifecycleProvider = activityLifecycleProviderWeakHashMap.get(activity);
            if (lifecycleProvider == null) {
                BehaviorSubject<ActivityEvent> subject = BehaviorSubject.create();
                lifecycleProvider = new LifeCycleProviderProxy<ActivityEvent>(subject) {
                    @Nonnull
                    @Override
                    public <T> LifecycleTransformer<T> bindToLifecycle() {
                        return RxLifecycleAndroid.bindActivity(subject);
                    }
                };
                activityLifecycleProviderWeakHashMap.put(activity, lifecycleProvider);
            }

            return lifecycleProvider;
        }
    }

    @SuppressWarnings({"SynchronizationOnLocalVariableOrMethodParameter", "unchecked"})
    public static LifeCycleProviderProxy<FragmentEvent> getFragmentLifecycleProvider(Fragment fragment) {
        synchronized (fragment) {
            LifeCycleProviderProxy<FragmentEvent> lifecycleProvider = frgamentLifecycleProviderWeakHashMap.get(fragment);
            if (lifecycleProvider == null) {
                BehaviorSubject<FragmentEvent> subject = BehaviorSubject.create();
                lifecycleProvider = new LifeCycleProviderProxy<FragmentEvent>(subject) {
                    @Nonnull
                    @Override
                    public <T> LifecycleTransformer<T> bindToLifecycle() {
                        return RxLifecycleAndroid.bindFragment(subject);
                    }
                };
                frgamentLifecycleProviderWeakHashMap.put(fragment, lifecycleProvider);
            }

            return lifecycleProvider;
        }
    }

    public static void installSupport(Application application) {
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    private static void installFragmentSupport(FragmentManager fragmentManager) {
        fragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks);
        fragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true);
    }
}
