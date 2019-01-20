package com.youzi.framework.common.util.req;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by LuoHaifeng on 2018/1/24 0024.
 * Email:496349136@qq.com
 */

public class ReqCompat {
    private static final String TAG = "com.github.robining.ReqCompatInnerFragment";

    public static void startActivityForResult(Activity activity, Intent intent, OnStartActivityResultCallback callback) {
        synchronized (activity) {
            if (activity instanceof FragmentActivity) {
                innerStartActivityForResultV4((FragmentActivity) activity, intent, callback);
            } else {
                innerStartActivityForResult(activity, intent, callback);
            }
        }
    }

    public static void startActivityForResult(Fragment fragment, Intent intent, OnStartActivityResultCallback callback) {
        startActivityForResult(fragment.getActivity(), intent, callback);
    }

    private static void innerStartActivityForResultV4(FragmentActivity activity, Intent intent, OnStartActivityResultCallback callback) {
        InnerProxyFragmentV4 fragment = (InnerProxyFragmentV4) activity.getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new InnerProxyFragmentV4();
            activity.getSupportFragmentManager().beginTransaction().add(fragment, TAG).commitNow();
        }
        fragment.startActivityForResult(intent, callback);
    }

    private static void innerStartActivityForResult(Activity activity, Intent intent, OnStartActivityResultCallback callback) {
        InnerProxyFragment fragment = (InnerProxyFragment) activity.getFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new InnerProxyFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                activity.getFragmentManager().beginTransaction().add(fragment, TAG).commitNow();
            }else {
                activity.getFragmentManager().beginTransaction().add(fragment, TAG).commit();
            }
        }
        fragment.startActivityForResult(intent, callback);
    }
}
