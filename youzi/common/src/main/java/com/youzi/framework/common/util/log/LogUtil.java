package com.youzi.framework.common.util.log;

import android.util.Log;

/**
 * Created by zzw on 2019/4/23.
 * 描述:
 */
public class LogUtil {
    private static final String TAG ="zzz";
    public static boolean isDebug = true;


    public static void d(String format, Object... args) {
        if (isDebug) {
            Log.d(TAG, String.format(format, args));
        }
    }

    public static void e (String format, Object... args) {
        if (isDebug) {
            Log.e(TAG, String.format(format, args));
        }
    }
}
