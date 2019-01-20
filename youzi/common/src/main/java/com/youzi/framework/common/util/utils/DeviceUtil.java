package com.youzi.framework.common.util.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.TypedValue;

import com.youzi.framework.common.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoHaifeng on 2018/2/11 0011.
 * Email:496349136@qq.com
 */

public class DeviceUtil {
    /**
     * 将px值转换为dp值，保证尺寸不便。
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        final float scale = Config.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dp值转换为px值，保持尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        final float scale = Config.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为dp值，保证尺寸不便。
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = Config.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将dp值转换为px值，保持尺寸大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = Config.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    //------------------------使用系统提供的TypedValue类进行转换--------------------
    protected int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    protected int sp2px(int sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    @SuppressLint("MissingPermission")
    public static List<String> getImei(Context context) {
        List<String> result = new ArrayList<>();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            String imei01 = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei01 = telephonyManager.getImei(0);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imei01 = telephonyManager.getDeviceId(0);
            } else {
                imei01 = telephonyManager.getDeviceId();
            }

            String imei02 = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei02 = telephonyManager.getImei(1);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imei02 = telephonyManager.getDeviceId(1);
            } else {
                imei02 = telephonyManager.getDeviceId();
            }

            if (imei01 != null) {
                result.add(imei01);
            }

            if (imei02 != null) {
                if (!result.contains(imei02))
                    result.add(imei02);
            }
        }

        return result;
    }
}